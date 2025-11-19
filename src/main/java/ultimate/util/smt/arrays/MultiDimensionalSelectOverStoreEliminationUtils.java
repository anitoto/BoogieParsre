/*
 * Copyright (C) 2019 Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
 * Copyright (C) 2019 University of Freiburg
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

import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

import ultimate.logic.Term;
import ultimate.util.smt.ArrayIndexEqualityManager;
import ultimate.util.smt.ArrayQuantifierEliminationUtils;
import ultimate.util.smt.IteRemover;
import ultimate.util.smt.SubstitutionWithLocalSimplification;
import ultimate.util.smt.managedscript.ManagedScript;
import ultimate.util.datastructures.EqualityStatus;

/**
 * @author Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
 * @author TOTO (Towzer)
 *
 */
public class MultiDimensionalSelectOverStoreEliminationUtils {

	public static Term replace(final ManagedScript mgdScript, final ArrayIndexEqualityManager aiem, final Term term,
			final MultiDimensionalSelectOverStore mdsos, final Logger logger) {
		final Map<Term, Term> substitutionMapping;
		final ArrayIndex selectIndex = mdsos.getSelect().getIndex();
		final ArrayIndex storeIndex = mdsos.getStore().getIndex();
		// final ThreeValuedEquivalenceRelation<Term> tver =
		// ArrayIndexEqualityUtils.analyzeIndexEqualities(mScript, selectIndex,
		// storeIndex, quantifier, xjunctsOuter);
		final EqualityStatus indexEquality = aiem.checkIndexEquality(selectIndex, storeIndex);
		Term result;
		switch (indexEquality) {
			case EQUAL:
				substitutionMapping = Collections.singletonMap(mdsos.toTerm(), mdsos.constructEqualsReplacement());
				result = new SubstitutionWithLocalSimplification(mgdScript, substitutionMapping, logger)
						.transform(term);
				break;
			case NOT_EQUAL:
				substitutionMapping = Collections.singletonMap(mdsos.toTerm(),
						mdsos.constructNotEqualsReplacement(mgdScript.getScript()));
				result = new SubstitutionWithLocalSimplification(mgdScript, substitutionMapping, logger)
						.transform(term);
				break;
			case UNKNOWN:
				substitutionMapping = Collections.singletonMap(mdsos.toTerm(), ArrayQuantifierEliminationUtils
						.transformMultiDimensionalSelectOverStoreToIte(mdsos, mgdScript, aiem));
				final Term resultWithIte = new SubstitutionWithLocalSimplification(mgdScript, substitutionMapping,
						logger)
						.transform(term);
				result = new IteRemover(mgdScript, logger).transform(resultWithIte);
				break;
			default:
				throw new AssertionError();
		}
		return result;
	}

	public static Term replace(final ManagedScript mgdScript, final ArrayIndexEqualityManager aiem, final Term term,
			final MultiDimensionalSelectOverNestedStore mdsos, final Logger logger) {
		final Map<Term, Term> substitutionMapping = Collections.singletonMap(mdsos.toTerm(),
				ArrayQuantifierEliminationUtils.transformMultiDimensionalSelectOverNestedStoreToIte(mdsos, mgdScript,
						aiem, logger));
		final Term resultWithIte = new SubstitutionWithLocalSimplification(mgdScript, substitutionMapping, logger)
				.transform(term);
		final Term result = new IteRemover(mgdScript, logger).transform(resultWithIte);
		return result;
	}

}
