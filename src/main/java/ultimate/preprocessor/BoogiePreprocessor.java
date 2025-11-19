/*
 * Copyright (C) 2008-2015 Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * Copyright (C) 2015 Jochen Hoenicke (hoenicke@informatik.uni-freiburg.de)
 * Copyright (C) 2015 University of Freiburg
 *
 * This file is part of the ULTIMATE BoogiePreprocessor plug-in.
 *
 * The ULTIMATE BoogiePreprocessor plug-in is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ULTIMATE BoogiePreprocessor plug-in is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ULTIMATE BoogiePreprocessor plug-in. If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional permission under GNU GPL version 3 section 7:
 * If you modify the ULTIMATE BoogiePreprocessor plug-in, or any covered work, by linking
 * or combining it with Eclipse RCP (or a modified version of Eclipse RCP),
 * containing parts covered by the terms of the Eclipse Public License, the
 * licensors of the ULTIMATE BoogiePreprocessor plug-in grant you additional permission
 * to convey the resulting work.
 */
package ultimate.preprocessor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ultimate.models.IElement;
import ultimate.models.observers.IObserver;
import ultimate.symboltable.BoogieSymbolTable;
import ultimate.symboltable.BoogieSymbolTableConstructor;

import ultimate.ast.BoogieASTNode;
import ultimate.ast.Declaration;
import ultimate.ast.Unit;

/**
 * This class initializes the boogie preprocessor.
 *
 * @author hoenicke
 * @author dietsch@informatik.uni-freiburg.de (for backtranslation)
 * @author TOTO (Towzer)
 *
 */
public class BoogiePreprocessor {
	// Global Java logger
	private final Logger logger;

	// Flags
	private final boolean use_simplifier;

	// Object from the Main
	private final IElement root;

	private final BoogiePreprocessorBacktranslator backTranslator;
	private final BoogieSymbolTableConstructor symb;
	private ArrayList<IObserver> observers;

	public BoogiePreprocessor(
			final Logger logger,
			final boolean emit_backtranslation_warnings,
			final boolean use_simplifier,
			final IElement root) {
		this.logger = logger;
		this.use_simplifier = use_simplifier;
		this.root = root;

		backTranslator = new BoogiePreprocessorBacktranslator(logger, emit_backtranslation_warnings);
		symb = new BoogieSymbolTableConstructor(logger);
		backTranslator.setSymbolTable(symb.getSymbolTable());

		setupObservers();
	}

	private void setupObservers() {
		observers = new ArrayList<>();
		observers.add(new EnsureBoogieModelObserver());
		// You can use the DebugObserver here if needed
		observers.add(new TypeChecker(logger));
		observers.add(new ConstExpander(backTranslator));
		observers.add(new StructExpander(backTranslator));
		observers.add(new UnstructureCode(backTranslator));
		observers.add(new FunctionInliner());
		if (use_simplifier) {
			observers.add(new Simplifier(backTranslator));
		}
	}

	public void executeObservers() {
		for (IObserver obs : observers) {
			logger.info("Executing the observer " + obs.getClass().getSimpleName());
			try {
				Method processMethod = obs.getClass().getMethod("process", IElement.class);
				processMethod.invoke(obs, root);

				// do based on log level only because getASTNodeCount() is long
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("AST node count after observer: " + getASTNodeCount((Unit) root));
					logger.fine("Declaration count after observer: " + getDeclarationCount((Unit) root));
				}
			} catch (NoSuchMethodException e) {
				logger.warning("Observer " + obs.getClass().getSimpleName() + " does not implement process(IElement)");
			} catch (Exception e) {
				logger.severe(
						"Failed to execute the observer " + obs.getClass().getSimpleName() + ": " + e.getMessage());
				e.printStackTrace();
			}
		}

		logger.info("Executing the observer " + symb.getClass().getSimpleName());
		try {
			symb.init();
			symb.process(root);
			symb.finish();
			// do based on log level only because getASTNodeCount() is long
			if (logger.isLoggable(Level.FINE)) {
				logger.fine("AST node count after observer: " + getASTNodeCount((Unit) root));
				logger.fine("Declaration count after observer: " + getDeclarationCount((Unit) root));
			}
		} catch (Throwable e) {
			logger.severe("Failed to execute the observer " + symb.getClass().getSimpleName() + ": " + e.getMessage());
		}
	}

	public BoogieSymbolTable getBoogieSymbTable() {
		return symb.getSymbolTable();
	}

	// Measure AST size
	public int getASTNodeCount(IElement element) {
		if (element == null)
			return 0;

		int count = 1; // Count this node

		if (element instanceof Unit) {
			Unit unit = (Unit) element;
			if (unit.getDeclarations() != null) {
				for (Declaration decl : unit.getDeclarations()) {
					count += getASTNodeCount(decl);
				}
			}
		} else if (element instanceof BoogieASTNode) {
			BoogieASTNode node = (BoogieASTNode) element;
			for (BoogieASTNode child : node.getOutgoingNodes()) {
				count += getASTNodeCount(child);
			}
		}

		return count;
	}

	// Count declarations specifically
	public int getDeclarationCount(Unit unit) {
		if (unit == null || unit.getDeclarations() == null)
			return 0;
		return unit.getDeclarations().length;
	}
}
