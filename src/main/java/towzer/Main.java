/*
 * Copyright (C) 2025 Towzer
 */
package towzer;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

import towzer.output.OutputBuilder;
import towzer.preferences.AppConfig;

import ultimate.Storage;
import ultimate.ast.Unit;
import ultimate.blockencoding.BlockEncoding;
import ultimate.parser.BoogieParser;
import ultimate.preprocessor.BoogiePreprocessor;
import ultimate.rcfgbuilder.RCFGBuilder;
import ultimate.rcfgbuilder.cfg.BoogieIcfgLocation;
import ultimate.util.cfg.structure.IIcfg;

import utopia.specLang.PropertyContainer;
import utopia.specLang.UtopiaSpecLang;

// for getAutomatonStats()
import java.util.Set;
import java.util.stream.Collectors;
import ultimate.util.cfg.structure.IcfgEdge;
import ultimate.util.cfg.structure.IcfgLocationIterator;
import ultimate.util.cfg.structure.IcfgLocation;

/**
 */
public class Main {
	public static void main(String[] args) {
		if (args.length < 1 || args.length > 2) {
			System.err.println("Usage: java Main <file.bpl> [preferences]");
			System.err.println(
					"Preferences: default (use default values), user (use user values), setup (setup user values and use them)");
			System.exit(1);
		}

		String filePath = args[0];
		String mode = (args.length == 2) ? args[1].toLowerCase() : "default";

		String[] result = computePhiAndP(filePath, mode);
		if (result == null) {
			System.exit(2);
		}
	}

	public static String[] computePhiAndP(String filePath, String mode) {
		/** ---------- Handle program arguments ---------- */

		File boogieFile = new File(filePath);
		if (!boogieFile.exists()) {
			System.err.println("File does not exist: " + filePath);
			return new String[] { "ERROR", "File does not exist: " + filePath, "0", "0", "0", "0", "0", "0", "0" };
		}

		/** ---------- Handle preferences ---------------- */

		boolean configModified = false;

		switch (mode) {
			case "default":
				// nothing, use default values
				break;

			case "user":
				try {
					AppConfig.loadFromJson("user_preferences.json");
				} catch (IOException e) {
					System.out.println("No existing preferences found. Starting setup...");
					AppConfig.interactiveSetup();
					configModified = true;
				}
				break;

			case "setup":
				AppConfig.interactiveSetup();
				configModified = true;
				break;

			default:
				System.err.println("Invalid second argument: " + mode);
				System.err.println("Expected: default, user, or setup");
				return new String[] { "ERROR", "Invalid second argument: " + mode, "0", "0", "0", "0", "0", "0", "0" };
		}

		if (configModified) {
			try {
				AppConfig.saveToJson("user_preferences.json");
				System.out.println("Preferences saved.");
			} catch (IOException e) {
				System.err.println("Could not save preferences.");
			}
		}

		/** ---------- Setup Java Logger ----------------- */

		Logger logger = Logger.getLogger("BoogieParserLogger");
		logger.setUseParentHandlers(false);
		Level logLevel = AppConfig.log_level.get();

		// Create a StreamHandler to write to System.out
		StreamHandler handler = new StreamHandler(System.out, new CustomFormatter()) {
			@Override
			public synchronized void publish(LogRecord record) {
				super.publish(record);
				flush(); // Ensure output appears immediately
			}
		};

		handler.setLevel(logLevel);
		logger.addHandler(handler);
		logger.setLevel(logLevel);

		/** ---------- Main code ------------------------- */

		logger.info("------------------------ Boogie PL CUP Parser --------------------------------");
		long start = System.nanoTime();
		final BoogieParser parser = new BoogieParser(logger);
		Unit ast = null;
		try {
			ast = parser.parse(boogieFile);
			logger.info("Successfully parsed: " + boogieFile.getAbsolutePath());
		} catch (IOException e) {
			logger.severe("I/O error during parsing: " + e.getMessage());
			return new String[] { "ERROR", "I/O error during parsing: " + e.getMessage(), "0", "0", "0", "0", "0", "0", "0" };
		} catch (RuntimeException e) {
			logger.severe("Parsing failed: " + e.getMessage());
			return new String[] { "ERROR", "Parsing failed: " + e.getMessage(), "0", "0", "0", "0", "0", "0", "0" };
		}
		long parserTime = System.nanoTime() - start;
		logger.info("------------------------ END Boogie PL CUP Parser ----------------------------");

		// Null check just in case
		if (ast == null) {
			return new String[] { "ERROR", "AST is null after parsing", "0", "0", "0", "0", "0", "0", "0" };
		}

		logger.info("------------------------ Boogie Preprocessor ---------------------------------");
		start = System.nanoTime();
		final BoogiePreprocessor preprocessor;
		try {
			preprocessor = new BoogiePreprocessor(
					logger,
					AppConfig.emit_backtranslation_warnings.get(),
					AppConfig.use_simplifier.get(),
					ast);

			// do based on log level only because getASTNodeCount() is long
			if (logger.isLoggable(Level.INFO)) {
				logger.info("AST node count after parsing: " + preprocessor.getASTNodeCount(ast));
				logger.info("Declaration count after parsing: " + preprocessor.getDeclarationCount(ast));
			}

			preprocessor.executeObservers();

			// do based on log level only because getASTNodeCount() is long
			if (logger.isLoggable(Level.INFO)) {
				logger.info("AST node count after preprocessing: " + preprocessor.getASTNodeCount(ast));
				logger.info("Declaration count after preprocessing: " + preprocessor.getDeclarationCount(ast));
			}
		} catch (Exception e) {
			logger.severe("Preprocessing failed: " + e.getMessage());
			return new String[] { "ERROR", "Preprocessing failed: " + e.getMessage(), "0", "0", "0", "0", "0", "0", "0" };
		}
		long preprocessorTime = System.nanoTime() - start;
		logger.info("------------------------ END Boogie Preprocessor -----------------------------");

		logger.info("------------------------ UtopiaSpecLang --------------------------------------");
		start = System.nanoTime();
		final PropertyContainer property;
		try {
			UtopiaSpecLang specLang = new UtopiaSpecLang(logger, ast);
			property = specLang.executeObservers();
		} catch (Exception e) {
			logger.severe("UtopiaSpecLang failed.");
			return new String[] { "ERROR", "UtopiaSpecLang failed: " + e.getMessage(), "0", "0", "0", "0", "0", "0", "0" };
		}
		long specLangTime = System.nanoTime() - start;
		logger.info("------------------------ END UtopiaSpecLang ----------------------------------");

		logger.info("------------------------ RCFGBuilder -----------------------------------------");
		start = System.nanoTime();
		final IIcfg<BoogieIcfgLocation> icfg;
		Storage storage = new Storage(logger);
		try {
			// do based on log level only because getASTNodeCount() is long
			if (logger.isLoggable(Level.INFO)) {
				logger.info("AST node count: " + preprocessor.getASTNodeCount(ast));
				logger.info("Declaration count: " + preprocessor.getDeclarationCount(ast));
			}

			RCFGBuilder rcfgBuilder = new RCFGBuilder(logger, storage, ast);
			icfg = rcfgBuilder.executeObservers();
			printIcfgStats(icfg, logger);
		} catch (Exception e) {
			logger.severe("RCFGBuilder failed: " + e.getMessage());
			return new String[] { "ERROR", "RCFGBuilder failed: " + e.getMessage(), "0", "0", "0", "0", "0", "0", "0" };
		}
		long rcfgBuilderTime = System.nanoTime() - start;
		logger.info("------------------------ END RCFGBuilder -------------------------------------");

		logger.info("------------------------ BlockEncoding ---------------------------------------");
		start = System.nanoTime();
		final IIcfg<IcfgLocation> icfgEncoded;
		try {
		BlockEncoding blockEncoding = new BlockEncoding(logger, storage, icfg);
		icfgEncoded = blockEncoding.executeObservers();
		} catch (Exception e) {
			logger.severe("BlockEncoding failed: " + e.getMessage());
			return new String[] { "ERROR", "BlockEncoding failed: " + e.getMessage(), "0", "0", "0", "0", "0", "0", "0" };
		}
		long blockEncodingTime = System.nanoTime() - start;
		logger.info("------------------------ END BlockEncoding -----------------------------------");

		logger.info("------------------------ Output Builder --------------------------------------");
		start = System.nanoTime();
		final String phi, P, boogieSymbTable;
		try {
			final OutputBuilder outputBuilder = new OutputBuilder(AppConfig.interprocedural.get(), logger);
			phi = outputBuilder.formatProperty(property);
			P = outputBuilder.formatProgramAut(icfgEncoded);
			boogieSymbTable = outputBuilder.formatBoogieSymbTable(preprocessor.getBoogieSymbTable());
			logger.info("--- Intégration C++ Spot phi :\n" + phi);
			logger.info("--- Intégration C++ Spot P :\n" + P);
			logger.info("--- Intégration C++ SMT solver Boogie symbol table :\n" + boogieSymbTable);
		} catch (Exception e) {
			logger.severe("Output Builder failed: " + e.getMessage());
			return new String[] { "ERROR", "Output Builder failed: " + e.getMessage(), "0", "0", "0", "0", "0", "0", "0" };
		}
		long outputBuilderTime = System.nanoTime() - start;
		logger.info("------------------------ END Output Builder ----------------------------------");

		// Return values: phi, P, Boogie symbol table and timings
		return new String[] {
				phi,
				P,
				boogieSymbTable,
				String.format("%.2f", parserTime / 1_000_000.0),
				String.format("%.2f", preprocessorTime / 1_000_000.0),
				String.format("%.2f", specLangTime / 1_000_000.0),
				String.format("%.2f", rcfgBuilderTime / 1_000_000.0),
				String.format("%.2f", blockEncodingTime / 1_000_000.0),
				String.format("%.2f", outputBuilderTime / 1_000_000.0)
		};
	}

	private static void printIcfgStats(final IIcfg<BoogieIcfgLocation> icfg, final Logger logger) {
		final IcfgLocationIterator<?> iter = new IcfgLocationIterator<>(icfg);
		final Set<IcfgLocation> allNodes = iter.asStream().collect(Collectors.toSet());

		int nbEdges = 0;
		for (final IcfgLocation locNode : allNodes) {
			if (locNode.getOutgoingNodes() != null) {
				for (final IcfgEdge edge : locNode.getOutgoingEdges()) {
					nbEdges++;
				}
			}
		}

		String stats = "Stats RCFG\n" +
				"States: " + allNodes.size() + "\n" +
				"Edges: " + nbEdges;
		logger.info(stats);
	}
}
