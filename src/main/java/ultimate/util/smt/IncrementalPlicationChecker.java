/*
 * Copyright (C) 2017 Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
 * Copyright (C) 2017 University of Freiburg
 *
 * This file is part of the ULTIMATE LassoRanker Library.
 *
 * The ULTIMATE LassoRanker Library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ULTIMATE LassoRanker Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ULTIMATE LassoRanker Library. If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional permission under GNU GPL version 3 section 7:
 * If you modify the ULTIMATE LassoRanker Library, or any covered work, by linking
 * or combining it with Eclipse RCP (or a modified version of Eclipse RCP),
 * containing parts covered by the terms of the Eclipse Public License, the
 * licensors of the ULTIMATE LassoRanker Library grant you additional permission
 * to convey the resulting work.
 */
package ultimate.util.smt;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import ultimate.logic.Script.LBool;
import ultimate.logic.Term;
import ultimate.logic.TermVariable;
import ultimate.util.hoaretriple.IHoareTripleChecker;
import ultimate.util.hoaretriple.IHoareTripleChecker.Validity;
import ultimate.util.smt.managedscript.ManagedScript;

/**
 * Check validity of an implication between two formulas
 * antecedent ==> succedent
 * The check is done incrementally in the sense that we can do it for
 * several succedents.
 * We presume that the succedent may have only variables that occurred in the
 * antecedent (because we have to replace variables by fresh constants and
 * these constants and determined when asserting the antecedent.
 * 
 * @author Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
 * @author TOTO (Towzer)
 */
public class IncrementalPlicationChecker {
	// Global Java logger
	private final Logger logger;

	public enum Plication {
		IMPLICATION, EXPLICATION
	};

	private final ManagedScript mMgdScript;
	private final Term mLhs;
	private boolean mLhsIsAsserted;
	private Substitution mVar2ConstSubstitution;
	private final Plication mPlication;

	public IncrementalPlicationChecker(final Plication plication, final ManagedScript mgdScript, final Term lhs,
			final Logger logger) {
		super();
		this.logger = logger;
		mPlication = plication;
		mMgdScript = mgdScript;
		mLhs = lhs;
		mLhsIsAsserted = false;
	}

	private void assertLhs(final Term lhs) {
		assert !mLhsIsAsserted : "must not assert lhs twice";
		mMgdScript.lock(this);
		mMgdScript.push(this, 1);
		mVar2ConstSubstitution = constructVar2ConstSubstitution(lhs);
		final Term assertTerm;
		switch (mPlication) {
			case EXPLICATION:
				assertTerm = SmtUtils.not(mMgdScript.getScript(), lhs, logger);
				break;
			case IMPLICATION:
				assertTerm = lhs;
				break;
			default:
				throw new AssertionError("unknown case");
		}
		mMgdScript.assertTerm(this, mVar2ConstSubstitution.transform(assertTerm));
		mLhsIsAsserted = true;
	}

	/**
	 * Construct a substitution that replaces all free TermVariables of lhs
	 * by constants and declares these constants.
	 */
	private Substitution constructVar2ConstSubstitution(final Term term) {
		final Set<TermVariable> allTvs = new HashSet<>(Arrays.asList(term.getFreeVars()));
		final Map<TermVariable, Term> substitutionMapping = SmtUtils.termVariables2Constants(mMgdScript.getScript(),
				allTvs, true);
		final Substitution subst = new Substitution(mMgdScript, substitutionMapping, logger);
		return subst;
	}

	public Validity checkPlication(final Term rhs) {
		if (!mLhsIsAsserted) {
			assertLhs(mLhs);
		}
		mMgdScript.push(this, 1);
		final Term assertTerm;
		switch (mPlication) {
			case EXPLICATION:
				assertTerm = rhs;
				break;
			case IMPLICATION:
				assertTerm = SmtUtils.not(mMgdScript.getScript(), rhs, logger);
				break;
			default:
				throw new AssertionError("unknown case");
		}
		mMgdScript.assertTerm(this, mVar2ConstSubstitution.transform(assertTerm));
		final LBool isSat = mMgdScript.checkSat(this);
		mMgdScript.pop(this, 1);
		return IHoareTripleChecker.convertLBool2Validity(isSat);
	}

	public LBool checkSat(final Term additionalTerm) {
		if (!mLhsIsAsserted) {
			assertLhs(mLhs);
		}
		mMgdScript.push(this, 1);
		final Term assertTerm;
		switch (mPlication) {
			case EXPLICATION:
				assertTerm = additionalTerm;
				break;
			case IMPLICATION:
				assertTerm = additionalTerm;
				// assertTerm = SmtUtils.not(mMgdScript.getScript(), additionalTerm);
				break;
			default:
				throw new AssertionError("unknown case");
		}
		mMgdScript.assertTerm(this, mVar2ConstSubstitution.transform(assertTerm));
		final LBool isSat = mMgdScript.checkSat(this);
		mMgdScript.pop(this, 1);
		return isSat;
	}

	public void unlockSolver() {
		if (mLhsIsAsserted) {
			mMgdScript.pop(this, 1);
			mMgdScript.unlock(this);
		} else {
			// We did not assert the lhs, hence we did not lock the solver.
		}
	}
}
