/*
 * Copyright (C) 2013-2015 Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * Copyright (C) 2012-2015 Oday Jubran
 * Copyright (C) 2015 University of Freiburg
 *
 * This file is part of the ULTIMATE SMTSolverBridge.
 *
 * The ULTIMATE SMTSolverBridge is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ULTIMATE SMTSolverBridge is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ULTIMATE SMTSolverBridge. If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional permission under GNU GPL version 3 section 7:
 * If you modify the ULTIMATE SMTSolverBridge, or any covered work, by linking
 * or combining it with Eclipse RCP (or a modified version of Eclipse RCP),
 * containing parts covered by the terms of the Eclipse Public License, the
 * licensors of the ULTIMATE SMTSolverBridge grant you additional permission
 * to convey the resulting work.
 */
package ultimate.smtsolver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.github.jhoenicke.javacup.runtime.Symbol;

import ultimate.Storage;
import ultimate.exceptions.ToolchainCanceledException;
import ultimate.logic.Assignments;
import ultimate.logic.SMTLIBException;
import ultimate.logic.Script;
import ultimate.logic.Script.LBool;
import ultimate.logic.Term;
import ultimate.util.MonitoredProcess;

/**
 * This class runs an external SMT solver. The main methods are
 * <code>input</code>, which gives an input to the SMT
 * solver, and the <code>parse...</code> methods, which parse the output from
 * the SMT solver.
 *
 * @author Oday Jubran
 * @author Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * @author Matthias Heizmann
 * @author TOTO (Towzer)
 */
class Executor {
	// Global Java logger
	private final Logger logger;

	private final Storage storage;

	private MonitoredProcess mProcess;
	private Lexer mLexer;
	private BufferedWriter mWriter;
	private InputStream mStdErr;

	private final Script mScript;
	private final String mSolverCmd;
	private final String mName;

	private static final String sEofErrorMessage = "Received EOF on stdin.";

	Executor(final String solverCommand, final Script script, final String solverName, final Logger logger, final Storage storage)
			throws IOException {
		this.logger = logger;
		this.storage = storage;
		mSolverCmd = solverCommand;
		mScript = script;
		mName = solverName;
		createProcess();
	}

	private void createProcess() throws IOException {
		mProcess = MonitoredProcess.exec(mSolverCmd, "(exit)", logger, storage);
		mProcess.setTerminationAfterToolchainTimeout(20 * 1000);

		if (mProcess == null) {
			final String errorMsg = getLogStringPrefix() + " Could not create process, terminating... ";
			logger.severe(errorMsg);
			throw new IllegalStateException(errorMsg);
		}

		final OutputStream stdin = mProcess.getOutputStream();
		final InputStream stdout = mProcess.getInputStream();

		mStdErr = mProcess.getErrorStream();

		final MySymbolFactory symfactory = new MySymbolFactory();
		mLexer = new Lexer(new InputStreamReader(stdout));
		mLexer.setSymbolFactory(symfactory);

		mWriter = new BufferedWriter(new OutputStreamWriter(stdin));

		input("(set-option :print-success true)");
		parseSuccess();
	}

	public void input(final String in) {
		logger.fine(getLogStringPrefix() + " " + in);
		try {
			mWriter.write(in + "\n");
			mWriter.flush();
		} catch (final IOException e) {
			if (storage.getProgressMonitorService().continueProcessingRoot()) {
				throw new SMTLIBException(getLogStringPrefix() + " Connection to SMT solver broken", e);
			}
			throw new ToolchainCanceledException(getClass());
		}
	}

	public void exit() {
		input("(exit)");
		// 2015-11-12 Matthias: Do not parse "success" after exit.
		// Some solvers do return success (Barcelogic, CVC4, Z3) some solvers
		// don't do it (Princess, SMTInterpol).
		// parseSuccess();
		mProcess.forceShutdown();
		mProcess = null;
	}

	public static List<Symbol> parseSexpr(final Lexer lexer) throws IOException {
		final ArrayList<Symbol> result = new ArrayList<>();
		int parenLevel = 0;
		do {
			final Symbol sym = lexer.next_token();
			if (sym.sym == LexerSymbols.LPAR) {
				parenLevel++;
			} else if (sym.sym == LexerSymbols.RPAR) {
				parenLevel--;
			}
			result.add(sym);
		} while (parenLevel > 0);
		return result;
	}

	private List<Symbol> readAnswer() {
		try {
			final List<Symbol> result = parseSexpr(mLexer);
			for (final Symbol s : result) {
				logger.fine(s.toString());
			}
			return result;
		} catch (final IOException e) {
			throw new SMTLIBException(getLogStringPrefix() + " Connection to SMT solver broken", e);
		}
	}

	public void reset() throws IOException {
		try {
			mWriter.write("(exit)\n");
			mWriter.flush();
		} catch (final IOException e) {
			/* ignore */
		}
		mProcess.forceShutdown();
		createProcess();
	}

	public Symbol parse(final int what) {
		final List<Symbol> answer = readAnswer();
		String stderr = "";

		// clear the std error buffer as it blocks when it runs full
		try {
			if (mStdErr.available() > 0) {
				final StringBuilder sb = new StringBuilder();
				while (mStdErr.available() > 0) {
					final int i = mStdErr.read();
					final char c = (char) i;
					sb.append(c);
				}
				stderr = sb.toString();
				logger.warning(getLogStringPrefix() + " " + generateStderrMessage(stderr));
			}
		} catch (final IOException e) {
			// we don't care what happens on stdErr
		}

		final Parser parser = new Parser();
		parser.setScript(mScript);
		answer.add(0, new Symbol(what));
		parser.setAnswer(answer);
		try {
			return parser.parse();
		} catch (final SMTLIBException ex) {
			if (ex.getMessage().equals(Parser.s_EOF)) {
				throw new SMTLIBException(getLogStringPrefix() + sEofErrorMessage + " " + generateStderrMessage(stderr),
						ex);
			}
			throw ex;
		} catch (final UnsupportedOperationException ex) {
			throw ex;
		} catch (final Exception ex) {
			throw new SMTLIBException(
					getLogStringPrefix() + "Unexpected Exception while parsing. " + generateStderrMessage(stderr), ex);
		}
	}

	public void parseSuccess() {
		parse(LexerSymbols.SUCCESS);
	}

	public LBool parseCheckSatResult() {
		return (LBool) parse(LexerSymbols.CHECKSAT).value;
	}

	public Term[] parseGetAssertionsResult() {
		return (Term[]) parse(LexerSymbols.GETASSERTIONS).value;
	}

	public Term[] parseGetUnsatCoreResult() {
		return (Term[]) parse(LexerSymbols.GETUNSATCORE).value;
	}

	@SuppressWarnings("unchecked")
	public Map<Term, Term> parseGetValueResult() {
		return (Map<Term, Term>) parse(LexerSymbols.GETVALUE).value;
	}

	public Assignments parseGetAssignmentResult() {
		return (Assignments) parse(LexerSymbols.GETASSIGNMENT).value;
	}

	public Object[] parseGetInfoResult() {
		return (Object[]) parse(LexerSymbols.GETINFO).value;
	}

	public Object parseGetOptionResult() {
		return parse(LexerSymbols.GETOPTION).value;
	}

	public Term[] parseInterpolants() {
		return (Term[]) parse(LexerSymbols.GETINTERPOLANTS).value;
	}

	public Term parseTerm() {
		return (Term) parse(LexerSymbols.GETTERM).value;
	}

	private String getLogStringPrefix() {
		return mName + " (" + mSolverCmd + ")";
	}

	private static String generateStderrMessage(final String stderr) {
		if (stderr.isEmpty()) {
			return "No stderr output.";
		}
		return "stderr output: " + stderr;
	}

}
