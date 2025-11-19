/*
 * Copyright (C) 2014-2015 Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * Copyright (C) 2012-2015 Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
 * Copyright (C) 2012-2015 University of Freiburg
 *
 * This file is part of the ULTIMATE ModelCheckerUtils Library.
 *
 * The ULTIMATE ModelCheckerUtils Library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ULTIMATE ModelCheckerUtils Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ULTIMATE ModelCheckerUtils Library. If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional permission under GNU GPL version 3 section 7:
 * If you modify the ULTIMATE ModelCheckerUtils Library, or any covered work, by linking
 * or combining it with Eclipse RCP (or a modified version of Eclipse RCP),
 * containing parts covered by the terms of the Eclipse Public License, the
 * licensors of the ULTIMATE ModelCheckerUtils Library grant you additional permission
 * to convey the resulting work.
 */
package ultimate.util.boogie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import ultimate.Storage;
import ultimate.ast.Axiom;
import ultimate.ast.BoogieASTNode;
import ultimate.ast.DeclarationInformation;
import ultimate.ast.DeclarationInformation.StorageClass;
import ultimate.logic.QuotedObject;
import ultimate.logic.Script;
import ultimate.logic.Term;
import ultimate.util.boogie.Expression2Term.IIdentifierTranslator;
import ultimate.util.cfg.SmtSymbols;
import ultimate.util.cfg.variables.IProgramVar;
import ultimate.util.smt.SmtUtils;
import ultimate.util.smt.managedscript.ManagedScript;
import ultimate.util.smt.predicates.TermVarsProc;

/**
 * Main class for the translation from Boogie to SMT. Constructs other Objects
 * needed for this translation.
 *
 * @author Matthias Heizmann
 * @author TOTO (Towzer)
 *
 */
public class Boogie2SMT {

	private final BoogieDeclarations mBoogieDeclarations;
	private final ManagedScript mScript;

	private final TypeSortTranslator mTypeSortTranslator;
	private final IOperationTranslator mOperationTranslator;
	private final Boogie2SmtSymbolTable mBoogie2SmtSymbolTable;
	private final Expression2Term mExpression2Term;
	private final Term2Expression mTerm2Expression;

	private final Statements2TransFormula mStatements2TransFormula;

	private final SmtSymbols mSmtSymbols;

	public Boogie2SMT(
			final ManagedScript maScript,
			final BoogieDeclarations boogieDeclarations,
			final boolean bitvectorInsteadOfInt,
			final boolean simplePartialSkolemization,
			final Logger logger,
			final Storage storage) {
		mBoogieDeclarations = boogieDeclarations;
		mScript = maScript;

		if (bitvectorInsteadOfInt) {
			mTypeSortTranslator = new TypeSortTranslatorBitvectorWorkaround(boogieDeclarations.getTypeDeclarations(),
					mScript.getScript(), logger);
			mBoogie2SmtSymbolTable = new Boogie2SmtSymbolTable(boogieDeclarations, mScript, mTypeSortTranslator,
					Collections.emptySet());
			// TODO: add concurIdVars to mBoogie2SmtSymbolTable
			mOperationTranslator = new BitvectorWorkaroundOperationTranslator(mBoogie2SmtSymbolTable,
					mScript.getScript(), logger);
			mExpression2Term = new Expression2Term(mScript.getScript(), mTypeSortTranslator, mBoogie2SmtSymbolTable,
					mOperationTranslator, mScript, logger);
		} else {
			mTypeSortTranslator = new TypeSortTranslator(boogieDeclarations.getTypeDeclarations(), mScript.getScript(),
					logger);
			mBoogie2SmtSymbolTable = new Boogie2SmtSymbolTable(boogieDeclarations, mScript, mTypeSortTranslator,
					Collections.emptySet());

			mOperationTranslator = new DefaultOperationTranslator(mBoogie2SmtSymbolTable, mScript.getScript(), logger);
			mExpression2Term = new Expression2Term(mScript.getScript(), mTypeSortTranslator, mBoogie2SmtSymbolTable,
					mOperationTranslator, mScript, logger);
		}

		final ArrayList<Term> axiomList = new ArrayList<>(boogieDeclarations.getAxioms().size());
		mScript.getScript().echo(new QuotedObject("Start declaration of axioms"));
		for (final Axiom decl : boogieDeclarations.getAxioms()) {
			final Term term = declareAxiom(decl, mExpression2Term);
			axiomList.add(term);
		}
		mScript.getScript().echo(new QuotedObject("Finished declaration of axioms"));
		final TermVarsProc tvp = TermVarsProc.computeTermVarsProc(
				SmtUtils.and(mScript.getScript(), axiomList),
				maScript.getScript(), mBoogie2SmtSymbolTable, logger);
		assert tvp.getVars().isEmpty() : "axioms must not have variables";
		mSmtSymbols = new SmtSymbols(tvp.getClosedFormula(), tvp.getProcedures(),
				mBoogie2SmtSymbolTable.getSmtFunction2SmtFunctionDefinition(), logger);

		mStatements2TransFormula = new Statements2TransFormula(this, mExpression2Term, simplePartialSkolemization,
				logger, storage);
		mTerm2Expression = new Term2Expression(mTypeSortTranslator, mBoogie2SmtSymbolTable, maScript, logger);

	}

	public Script getScript() {
		return mScript.getScript();
	}

	public ManagedScript getManagedScript() {
		return mScript;
	}

	public Term2Expression getTerm2Expression() {
		return mTerm2Expression;
	}

	public Expression2Term getExpression2Term() {
		return mExpression2Term;
	}

	static String quoteId(final String id) {
		return id;
	}

	public Boogie2SmtSymbolTable getBoogie2SmtSymbolTable() {
		return mBoogie2SmtSymbolTable;
	}

	public Statements2TransFormula getStatements2TransFormula() {
		return mStatements2TransFormula;
	}

	public BoogieDeclarations getBoogieDeclarations() {
		return mBoogieDeclarations;
	}

	public TypeSortTranslator getTypeSortTranslator() {
		return mTypeSortTranslator;
	}

	public SmtSymbols getSmtSymbols() {
		return mSmtSymbols;
	}

	private Term declareAxiom(final Axiom ax, final Expression2Term expression2term) {
		final ConstOnlyIdentifierTranslator coit = new ConstOnlyIdentifierTranslator();
		final IIdentifierTranslator[] its = new IIdentifierTranslator[] { coit };
		final Term closedTerm = expression2term.translateToTerm(its, ax.getFormula()).getTerm();
		mScript.getScript().assertTerm(closedTerm);
		return closedTerm;
	}

	public static void reportUnsupportedSyntax(final BoogieASTNode astNode, final String longDescription,
			final Logger logger) {
		logger.severe(longDescription);
	}

	public class ConstOnlyIdentifierTranslator implements IIdentifierTranslator {

		private final Set<BoogieConst> mNonTheoryConsts = new HashSet<>();

		public ConstOnlyIdentifierTranslator() {
		}

		@Override
		public Term getSmtIdentifier(final String id, final DeclarationInformation declInfo, final boolean isOldContext,
				final BoogieASTNode boogieASTNode) {
			if (declInfo.getStorageClass() != StorageClass.GLOBAL) {
				throw new AssertionError();
			}
			final BoogieConst bc = mBoogie2SmtSymbolTable.getBoogieConst(id);
			if (!bc.belongsToSmtTheory()) {
				mNonTheoryConsts.add(bc);
			}
			return bc.getDefaultConstant();
		}

		public Set<BoogieConst> getNonTheoryConsts() {
			return mNonTheoryConsts;
		}

	}

	/**
	 * Simple translator for local variables and global variables that does not
	 * provide any side-effects for getting
	 * inVars or outVars. Use this in combination with
	 * {@link ConstOnlyIdentifierTranslator} to get a translator that
	 * has the capability to translate expression to terms.
	 *
	 */
	public class LocalVarAndGlobalVarTranslator implements IIdentifierTranslator {

		@Override
		public Term getSmtIdentifier(final String id, final DeclarationInformation declInfo, final boolean isOldContext,
				final BoogieASTNode boogieASTNode) {
			final IProgramVar pv = mBoogie2SmtSymbolTable.getBoogieVar(id, declInfo, isOldContext);
			if (pv == null) {
				return null;
			}
			return pv.getTermVariable();
		}
	}
}
