/*
 * Copyright (C) 2017 Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
 * Copyright (C) 2017 University of Freiburg
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
package ultimate.util.smt.arrays;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import ultimate.logic.ApplicationTerm;
import ultimate.logic.Script;
import ultimate.logic.Term;
import ultimate.util.smt.ApplicationTermFinder;

/**
 * @author Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
 * @author TOTO (Towzer)
 *
 */
public class MultiDimensionalSelectOverStore {
	// Global Java logger
	private final Logger logger;

	private final MultiDimensionalSelect mSelect;
	private final MultiDimensionalStore mStore;
	private final Term mTerm;

	public MultiDimensionalSelectOverStore(final Term term, final Logger logger) {
		this.logger = logger;
		final MultiDimensionalSelect select = new MultiDimensionalSelect(term, logger);
		if (!select.getIndex().isEmpty()) {
			final Term innerArray = select.getArray();
			if (innerArray instanceof ApplicationTerm) {
				final MultiDimensionalStore store = MultiDimensionalStore.convert(innerArray, logger);
				if (store.getIndex().size() == select.getIndex().size()) {
					mSelect = select;
					mStore = store;
					mTerm = term;
					return;
				}
			}
		}
		mSelect = null;
		mStore = null;
		mTerm = null;
	}

	public MultiDimensionalSelect getSelect() {
		return mSelect;
	}

	public MultiDimensionalStore getStore() {
		return mStore;
	}

	public Term toTerm() {
		return mTerm;
	}

	public static MultiDimensionalSelectOverStore convert(final Term term, final Logger logger) {
		final MultiDimensionalSelectOverStore mdsos = new MultiDimensionalSelectOverStore(term, logger);
		if (mdsos.getSelect() == null) {
			return null;
		} else {
			return mdsos;
		}
	}

	/**
	 * @return Term that is equivalent to this
	 *         {@link MultiDimensionalSelectOverStore}
	 *         if the index of the select and the index of the store are distinct.
	 */
	public Term constructNotEqualsReplacement(final Script script) {
		final MultiDimensionalSelect mds = new MultiDimensionalSelect(getStore().getArray(), getSelect().getIndex(),
				script, logger);
		return mds.getSelectTerm();
	}

	/**
	 * @return Term that is equivalent to this
	 *         {@link MultiDimensionalSelectOverStore}
	 *         if the index of the select and the index of the store are equivalent.
	 */
	public Term constructEqualsReplacement() {
		return getStore().getValue();
	}

	/**
	 *
	 * @return List of all {@link MultiDimensionalSelectOverStore} that occur in
	 *         given term.
	 */
	public static List<MultiDimensionalSelectOverStore> extractMultiDimensionalSelectOverStores(final Term term,
			final Logger logger) {
		final List<MultiDimensionalSelectOverStore> result = new ArrayList<>();
		final Set<ApplicationTerm> allSelectTerms = new ApplicationTermFinder("select", false)
				.findMatchingSubterms(term);
		for (final ApplicationTerm selectTerm : allSelectTerms) {
			final MultiDimensionalSelectOverStore mdsos = MultiDimensionalSelectOverStore.convert(selectTerm, logger);
			if (mdsos != null) {
				result.add(mdsos);
			}
		}
		return result;
	}

	/**
	 *
	 * @return List of all {@link MultiDimensionalSelectOverStore} that occur in
	 *         given term and where
	 *         arr is first operand of the (inner) store.
	 */
	public static List<MultiDimensionalSelectOverStore> extractMultiDimensionalSelectOverStores(final Term term,
			final Term arr, final Logger logger) {
		final List<MultiDimensionalSelectOverStore> result = new ArrayList<>();
		final Set<ApplicationTerm> allSelectTerms = new ApplicationTermFinder("select", false)
				.findMatchingSubterms(term);
		for (final ApplicationTerm selectTerm : allSelectTerms) {
			final MultiDimensionalSelectOverStore mdsos = MultiDimensionalSelectOverStore.convert(selectTerm, logger);
			if (mdsos != null && mdsos.getStore().getArray() == arr) {
				result.add(mdsos);
			}
		}
		return result;
	}

}
