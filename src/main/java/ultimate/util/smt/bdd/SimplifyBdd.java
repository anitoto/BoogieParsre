/*
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
package ultimate.util.smt.bdd;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sf.javabdd.BDD;

import ultimate.Storage;
import ultimate.logic.Script;
import ultimate.logic.Script.LBool;
import ultimate.logic.Term;
import ultimate.logic.Util;
import ultimate.util.datastructures.Pair;
import ultimate.util.smt.SmtUtils;
import ultimate.util.smt.SmtUtils.SimplificationTechnique;
import ultimate.util.smt.managedscript.ManagedScript;

/**
 * Some BDD based simplification. TODO: More detailed documentation.
 *
 * @author Michael Steinle
 * @author TOTO (Towzer)
 *
 */
public class SimplifyBdd {
	// Global Java logger
	private final Logger logger;

	private final Storage storage;

	private final Script mScript;
	// TODO: 2016-05-09 Matthias: The following field might be be useless
	private final ManagedScript mMgdScript;

	public SimplifyBdd(final ManagedScript mgdScript, final Logger logger, final Storage storage) {
		super();
		this.logger = logger;
		this.storage = storage;
		mScript = mgdScript.getScript();
		mMgdScript = mgdScript;
	}

	/**
	 * Transform input into simplified term.
	 *
	 * @return Term which is logically equivalent to input.
	 */
	public Term transform(final Term input) {
		final CollectAtoms ca = new CollectAtoms();
		final List<Term> atoms = ca.getTerms(input);

		final BddBuilder bb = new BddBuilder();
		final BDD d = bb.buildBDD(input, atoms);

		final Term result = bddToTerm(d, atoms);
		assert assertSimplificationSound(input, result) : "simplification unsound";
		return result;
	}

	private boolean assertSimplificationSound(final Term input, final Term result) {
		final boolean isSound = Util.checkSat(mScript, mScript.term("distinct", input, result)) != LBool.SAT;
		if (isSound) {
			return true;
		}
		logger.severe("Simplification failed");
		logger.severe("Input:             " + input);
		logger.severe("Output:            " + result);
		logger.severe("Simplified output: "
				+ SmtUtils.simplify(mMgdScript, result, SimplificationTechnique.SIMPLIFY_DDA, logger, storage));

		return false;
	}

	public Term transformWithImplications(final Term input) {
		final CollectAtoms ca = new CollectAtoms();
		final List<Term> atoms = ca.getTerms(input);

		final BddBuilder bb = new BddBuilder();
		final BDD d = bb.buildBDD(input, atoms);

		return bddToTerm(d, atoms);
	}

	private Term bddToTerm(final BDD d, final List<Term> atoms) {
		if (d.isOne()) {
			return mScript.term("true");
		} else if (d.isZero()) {
			return mScript.term("false");
		}

		final Term low = bddToTerm(d.low(), atoms);
		final Term high = bddToTerm(d.high(), atoms);
		if (SmtUtils.isFalse(low) && SmtUtils.isFalse(high)) {
			return low;
		} else if (SmtUtils.isFalse(high)) {
			if (SmtUtils.isTrue(low)) {
				return mScript.term("not", atoms.get(d.var()));
			} else {
				return mScript.term("and", mScript.term("not", atoms.get(d.var())), low);
			}
		} else if (SmtUtils.isFalse(low)) {
			if (SmtUtils.isTrue(high)) {
				return atoms.get(d.var());
			} else {
				return mScript.term("and", atoms.get(d.var()), high);
			}
		}
		return mScript.term("or", mScript.term("and", atoms.get(d.var()), high),
				mScript.term("and", mScript.term("not", atoms.get(d.var())), low));
	}

	public Term transformToDNF(final Term input) {
		final CollectAtoms ca = new CollectAtoms();
		final List<Term> atoms = ca.getTerms(input);
		assert !atoms.isEmpty() : "How did I not find any atoms? " + input;

		final BddBuilder bb = new BddBuilder();
		final BDD d = bb.buildBDD(input, atoms);

		final List<Term> con = new ArrayList<>();
		for (final byte[] t : (List<byte[]>) d.allsat()) {
			final List<Term> lit = new ArrayList<>();
			for (int i = 0; i < t.length; i++) {
				if (t[i] == 0) {
					lit.add(SmtUtils.not(mScript, atoms.get(i), logger));
				} else if (t[i] == 1) {
					lit.add(atoms.get(i));
				} // ==-1 is don't care
			}
			con.add(SmtUtils.and(mScript, lit));
		}
		final Term result = SmtUtils.or(mScript, con);
		assert assertSimplificationSound(input, result) : "DNF transformation unsound. Input: " + input;
		return result;
	}

	public Term transformToCNF(final Term input) {
		final CollectAtoms ca = new CollectAtoms();
		final List<Term> atoms = ca.getTerms(input);

		final BddBuilder bb = new BddBuilder();
		final BDD d = bb.buildBDD(input, atoms).not();

		final List<Term> dis = new ArrayList<>();
		for (final byte[] t : (List<byte[]>) d.allsat()) {
			final List<Term> lit = new ArrayList<>();
			for (int i = 0; i < t.length; i++) {
				if (t[i] == 1) {
					lit.add(SmtUtils.not(mScript, atoms.get(i), logger));
				} else if (t[i] == 0) {
					lit.add(atoms.get(i));
				} // ==-1 is don't care
			}
			dis.add(SmtUtils.or(mScript, lit));
		}
		final Term result = SmtUtils.and(mScript, dis);
		assert assertSimplificationSound(input, result) : "CNF transformation unsound. Input: " + input;
		return result;
	}

	/**
	 * @return true iff the SMT solver was able to prove that t1 implies t2.
	 */
	private boolean implies(final Term t1, final Term t2) {
		mScript.push(1);
		mScript.assertTerm(t1);
		mScript.assertTerm(SmtUtils.not(mScript, t2, logger));
		final LBool result = mScript.checkSat();
		mScript.pop(1);
		return (result == LBool.UNSAT);
	}

	public List<Pair<Term, Term>> impliesPairwise(final List<Term> in) {
		final List<Pair<Term, Term>> res = new ArrayList<>();

		for (int i = 0; i < in.size(); i++) {
			mScript.push(1);
			mScript.assertTerm(in.get(i));
			for (int j = 0; j < in.size(); j++) {
				mScript.push(1);
				mScript.assertTerm(SmtUtils.not(mScript, in.get(j), logger));
				final LBool result = mScript.checkSat();
				if (result == LBool.UNSAT) {
					res.add(new Pair<>(in.get(i), in.get(j)));
				}
				mScript.pop(1);
			}
			mScript.pop(1);
		}
		return res;
	}
}
