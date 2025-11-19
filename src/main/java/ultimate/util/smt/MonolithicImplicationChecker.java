/*
 * Copyright (C) 2016 Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
 * Copyright (C) 2016 University of Freiburg
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
package ultimate.util.smt;

import java.util.logging.Logger;

import ultimate.logic.QuotedObject;
import ultimate.logic.Script.LBool;
import ultimate.logic.Term;
import ultimate.util.hoaretriple.IHoareTripleChecker;
import ultimate.util.hoaretriple.IHoareTripleChecker.Validity;
import ultimate.util.smt.managedscript.ManagedScript;
import ultimate.util.smt.predicates.IPredicate;

/**
 * Check implication between two formulas that each represent a set of
 * program states.
 * 
 * @author Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
 * @author TOTO (Towzer)
 *
 */
public class MonolithicImplicationChecker {
	// Global Java logger
	private final Logger logger;

	private final ManagedScript mManagedScript;

	public MonolithicImplicationChecker(final ManagedScript managedScript, final Logger logger) {
		super();
		this.logger = logger;
		mManagedScript = managedScript;
	}

	/**
	 * Check if implication antecedent ==> succedent is valid.
	 */
	public Validity checkImplication(final IPredicate antecedent, final boolean affirmAntecedentNeitherValidNorUnsat,
			final IPredicate succedent, final boolean affirmSuccedentNeitherValidNorUnsat) {
		return checkImplication(antecedent.getFormula(), antecedent.getClosedFormula(),
				affirmAntecedentNeitherValidNorUnsat,
				succedent.getFormula(), succedent.getClosedFormula(), affirmSuccedentNeitherValidNorUnsat);
	}

	/**
	 * Check if implication antecedent ==> succedent is valid.
	 */
	public Validity checkImplication(final Term antecedent, final Term antecedentClosedFormula,
			final boolean affirmAntecedentNeitherValidNorUnsat,
			final Term succedent, final Term succedentClosedFormula,
			final boolean affirmSuccedentNeitherValidNorUnsat) {
		if (affirmAntecedentNeitherValidNorUnsat && affirmSuccedentNeitherValidNorUnsat) {
			final Validity dataflowAnalysisResult = dataflowBasedImplicationCheck(antecedent, succedent);
			if (dataflowAnalysisResult == Validity.INVALID) {
				return dataflowAnalysisResult;
			}
		}
		if (mManagedScript.isLocked()) {
			mManagedScript.requestLockRelease();
		}
		mManagedScript.lock(this);
		mManagedScript.echo(this, new QuotedObject("Start implication check"));
		mManagedScript.push(this, 1);
		mManagedScript.assertTerm(this, antecedentClosedFormula);
		mManagedScript.assertTerm(this, SmtUtils.not(mManagedScript.getScript(), succedentClosedFormula, logger));
		final LBool lbool = mManagedScript.checkSat(this);
		mManagedScript.pop(this, 1);
		mManagedScript.echo(this, new QuotedObject("Finished implication check"));
		mManagedScript.unlock(this);
		return IHoareTripleChecker.convertLBool2Validity(lbool);
	}

	private Validity dataflowBasedImplicationCheck(final Term antecedent, final Term succedent) {
		return Validity.UNKNOWN;
	}

}
