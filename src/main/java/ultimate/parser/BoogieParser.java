/*
 * Copyright (C) 2013-2015 Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * Copyright (C) 2008-2015 Jochen Hoenicke (hoenicke@informatik.uni-freiburg.de)
 * Copyright (C) 2015 University of Freiburg
 * Copyright (C) 2025 Towzer
 *
 * This file is based on the ULTIMATE BoogiePLParser plug-in.
 */
package ultimate.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

import ultimate.ast.Unit;

/**
 *
 * @author TOTO (Towzer)
 *
 */
public class BoogieParser {
	// Global Java logger
	private final Logger logger;

	public BoogieParser(final Logger logger) {
		this.logger = logger;
	}

	public Unit parse(File file) throws IOException {
		if (!file.exists() || file.isDirectory()) {
			logger.severe("Invalid file: " + file.getAbsolutePath());
			throw new IllegalArgumentException("Invalid file");
		}

		logger.info("Parsing file: " + file.getAbsolutePath());

		final BoogieSymbolFactory symFactory = new BoogieSymbolFactory();
		final Lexer lexer = new Lexer(new FileInputStream(file));
		lexer.setSymbolFactory(symFactory);
		final Parser parser = new Parser(lexer, symFactory, logger);
		parser.setFileName(file.getAbsolutePath());

		try {
			return (Unit) parser.parse().value;
		} catch (Exception e) {
			logger.severe("Syntax error in Boogie file: " + e);
			throw new RuntimeException("Parsing failed", e);
		}
	}
}
