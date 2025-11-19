/*
 * Copyright (C) 2014-2015 Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
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
package ultimate.util.smt;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import ultimate.logic.AnnotatedTerm;
import ultimate.logic.ApplicationTerm;
import ultimate.logic.ConstantTerm;
import ultimate.logic.FunctionSymbol;
import ultimate.logic.LetTerm;
import ultimate.logic.NonRecursive;
import ultimate.logic.QuantifiedFormula;
import ultimate.logic.Term;
import ultimate.logic.TermVariable;

/**
 * Find all NonTheorySymbols that occur freely (not quantified) in a Term.
 * 
 * @author Matthias Heizmann
 * @author TOTO (Towzer)
 */
public class NonTheorySymbolFinder extends NonRecursive {

	protected Set<NonTheorySymbol<?>> mResult;
	protected Set<Term> mVisitedSubterms;

	public Set<NonTheorySymbol<?>> findNonTheorySymbols(final Term term, final Logger logger) {
		if (term == null) {
			throw new IllegalArgumentException();
		}
		mResult = new HashSet<>();
		mVisitedSubterms = new HashSet<>();
		run(new ConstantFindWalker(term, logger));
		for (final TermVariable tv : term.getFreeVars()) {
			mResult.add(new NonTheorySymbol.Variable(tv));
		}
		return mResult;
	}

	private class ConstantFindWalker extends TermWalker {
		// Global Java logger
		private final Logger logger;

		ConstantFindWalker(final Term term, final Logger logger) {
			super(term);
			this.logger = logger;
		}

		@Override
		public void walk(final NonRecursive walker, final ConstantTerm term) {
			// do nothing
		}

		@Override
		public void walk(final NonRecursive walker, final AnnotatedTerm term) {
			walker.enqueueWalker(new ConstantFindWalker(term.getSubterm(), logger));
		}

		@Override
		public void walk(final NonRecursive walker, final ApplicationTerm term) {
			if (mVisitedSubterms.contains(term)) {
				// subterm already visited, we will not find anything new
				return;
			}
			mVisitedSubterms.add(term);
			if (SmtUtils.isConstant(term)) {
				mResult.add(new NonTheorySymbol.Constant(term));
			} else {
				final FunctionSymbol functionSymbol = term.getFunction();
				if (!functionSymbol.isIntern()) {
					mResult.add(new NonTheorySymbol.Function(functionSymbol));
				}
			}
			for (final Term t : term.getParameters()) {
				walker.enqueueWalker(new ConstantFindWalker(t, logger));
			}
		}

		@Override
		public void walk(final NonRecursive walker, final LetTerm term) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void walk(final NonRecursive walker, final QuantifiedFormula term) {
			walker.enqueueWalker(new ConstantFindWalker(term.getSubformula(), logger));
		}

		@Override
		public void walk(final NonRecursive walker, final TermVariable term) {
			// do nothing
		}
	}

}
