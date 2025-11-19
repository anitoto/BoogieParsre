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
package ultimate.util.smt;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

import ultimate.Storage;
import ultimate.logic.QuantifiedFormula;
import ultimate.logic.Script;
import ultimate.logic.Term;
import ultimate.util.smt.SmtUtils.XnfConversionTechnique;
import ultimate.util.smt.managedscript.ManagedScript;
import ultimate.util.smt.normalforms.NnfTransformer;
import ultimate.util.smt.normalforms.NnfTransformer.QuantifierHandling;

/**
 * Provides static methods for handling of quantifiers and their finite connectives
 * (and/or)
 * @author Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
 * @author TOTO (Towzer)
 */
public class QuantifierUtils {

	private QuantifierUtils() {
		// do not instantiate
	}

	/**
	 * Compose term with outer operation of a XNF. For the case of existential quantification: Compose disjuncts to a
	 * disjunction.
	 */
	public static Term applyCorrespondingFiniteConnective(final Script script, final int quantifier,
			final Collection<Term> xjunctsOuter) {
		final Term result;
		if (quantifier == QuantifiedFormula.EXISTS) {
			result = SmtUtils.or(script, xjunctsOuter);
		} else if (quantifier == QuantifiedFormula.FORALL) {
			result = SmtUtils.and(script, xjunctsOuter);
		} else {
			throw new AssertionError("unknown quantifier");
		}
		return result;
	}

	public static Term applyCorrespondingFiniteConnective(final Script script, final int quantifier,
			final Term... xjunctsOuter) {
		return applyCorrespondingFiniteConnective(script, quantifier, Arrays.asList(xjunctsOuter));
	}

	/**
	 * Compose term with inner operation of a XNF. For the case of existential quantification: Compose atoms to a
	 * conjunction.
	 */
	public static Term applyDualFiniteConnective(final Script script, final int quantifier,
			final Collection<Term> xjunctsInner) {
		final Term result;
		if (quantifier == QuantifiedFormula.EXISTS) {
			result = SmtUtils.and(script, xjunctsInner);
		} else if (quantifier == QuantifiedFormula.FORALL) {
			result = SmtUtils.or(script, xjunctsInner);
		} else {
			throw new AssertionError("unknown quantifier");
		}
		return result;
	}

	public static Term applyDualFiniteConnective(final Script script, final int quantifier,
			final Term... xjunctsInner) {
		return applyDualFiniteConnective(script, quantifier, Arrays.asList(xjunctsInner));
	}

	/**
	 * Apply equals if quantifier is existential and not equals if quantifier is universal.
	 */
	public static Term applyDerOperator(final Script script, final int quantifier, final Term lhs, final Term rhs, final Logger logger) {
		Term result;
		if (quantifier == QuantifiedFormula.EXISTS) {
			result = SmtUtils.binaryEquality(script, lhs, rhs, logger);
		} else if (quantifier == QuantifiedFormula.FORALL) {
			result = SmtUtils.distinct(script, lhs, rhs, logger);
		} else {
			throw new AssertionError("unknown quantifier");
		}
		return result;
	}

	/**
	 * Apply not equals if quantifier is existential and equals if quantifier is universal.
	 */
	public static Term applyAntiDerOperator(final Script script, final int quantifier, final Term lhs, final Term rhs, final Logger logger) {
		Term result;
		if (quantifier == QuantifiedFormula.EXISTS) {
			result = SmtUtils.distinct(script, lhs, rhs, logger);
		} else if (quantifier == QuantifiedFormula.FORALL) {
			result = SmtUtils.binaryEquality(script, lhs, rhs, logger);
		} else {
			throw new AssertionError("unknown quantifier");
		}
		return result;
	}

	/**
	 * Get all parameters of the outer operation of a XNF For the case of existential quantification: Get all disjuncts
	 * of a formula in DNF. (conjuncts of CNF for case of universal quantification)
	 */
	public static Term[] getXjunctsOuter(final int quantifier, final Term xnf) {
		Term[] xjunctsOuter;
		if (quantifier == QuantifiedFormula.EXISTS) {
			xjunctsOuter = SmtUtils.getDisjuncts(xnf);
		} else if (quantifier == QuantifiedFormula.FORALL) {
			xjunctsOuter = SmtUtils.getConjuncts(xnf);
		} else {
			throw new AssertionError("unknown quantifier");
		}
		return xjunctsOuter;
	}

	/**
	 * Get all parameters of the inner operation of a XNF For the case of existential quantification: Get all conjuncts
	 * of a conjunction. (disjuncts of disjunction in case of universal quantification)
	 */
	public static Term[] getXjunctsInner(final int quantifier, final Term xnf) {
		Term[] xjunctsOuter;
		if (quantifier == QuantifiedFormula.EXISTS) {
			xjunctsOuter = SmtUtils.getConjuncts(xnf);
		} else if (quantifier == QuantifiedFormula.FORALL) {
			xjunctsOuter = SmtUtils.getDisjuncts(xnf);
		} else {
			throw new AssertionError("unknown quantifier");
		}
		return xjunctsOuter;
	}


	public static Term getNeutralElement(final Script script, final int quantifier) {
		if (quantifier == QuantifiedFormula.EXISTS) {
			return script.term("false");
		} else if (quantifier == QuantifiedFormula.FORALL) {
			return script.term("true");
		} else {
			throw new AssertionError("unknown quantifier");
		}
	}

	public static Term getAbsorbingElement(final Script script, final int quantifier) {
		if (quantifier == QuantifiedFormula.EXISTS) {
			return script.term("true");
		} else if (quantifier == QuantifiedFormula.FORALL) {
			return script.term("false");
		} else {
			throw new AssertionError("unknown quantifier");
		}
	}


	/**
	 * Transform to DNF for existential quantifier,
	 * transform to CNF for universal quantifier.
	 */
	public static Term transformToXnf(final Script script, final int quantifier, final ManagedScript freshTermVariableConstructor, Term term, final XnfConversionTechnique xnfConversionTechnique, final Logger logger, final Storage storage) throws AssertionError {
		if (quantifier == QuantifiedFormula.EXISTS) {
			term = SmtUtils.toDnf(freshTermVariableConstructor, term, xnfConversionTechnique, logger, storage);
		} else if (quantifier == QuantifiedFormula.FORALL) {
			term = SmtUtils.toCnf(freshTermVariableConstructor, term, xnfConversionTechnique, logger, storage);
		} else {
			throw new AssertionError("unknown quantifier");
		}
		return term;
	}


	public static int getCorrespondingQuantifier(final String booleanConnective) {
		if (booleanConnective.equals("and")) {
			return QuantifiedFormula.FORALL;
		} else if (booleanConnective.equals("or")) {
			return QuantifiedFormula.EXISTS;
		} else {
			throw new AssertionError("unsupported connective " + booleanConnective);
		}
	}


	public static String getDualBooleanConnective(final String booleanConnective) {
		if (booleanConnective.equals("and")) {
			return "or";
		} else if (booleanConnective.equals("or")) {
			return "and";
		} else {
			throw new AssertionError("unsupported connective " + booleanConnective);
		}
	}

	/**
	 * Return inputTerm if quantifier is existential, negate and transform to NNF if
	 * quantifier is universal.
	 */
	public static Term negateIfUniversal(final ManagedScript mgdScript, final int quantifier, final Term inputTerm, final Logger logger) {
		Term result;
		if (quantifier == QuantifiedFormula.EXISTS) {
			result = inputTerm;
		} else if (quantifier == QuantifiedFormula.FORALL) {
			result = new NnfTransformer(mgdScript, QuantifierHandling.IS_ATOM, logger)
					.transform(SmtUtils.not(mgdScript.getScript(), inputTerm, logger));
		} else {
			throw new AssertionError("unknown quantifier");
		}
		return result;
	}

	/**
	 * Return inputTerm if quantifier is existential, negate and transform to NNF if
	 * quantifier is universal.
	 */
	public static Term negateIfExistential(final ManagedScript mgdScript, final int quantifier, final Term inputTerm, final Logger logger) {
		Term result;
		if (quantifier == QuantifiedFormula.EXISTS) {
			result = new NnfTransformer(mgdScript, QuantifierHandling.IS_ATOM, logger)
					.transform(SmtUtils.not(mgdScript.getScript(), inputTerm, logger));
		} else if (quantifier == QuantifiedFormula.FORALL) {
			result = inputTerm;
		} else {
			throw new AssertionError("unknown quantifier");
		}
		return result;
	}

	public static int getDualQuantifier(final int quantifier) {
		if (quantifier == 0) {
			return 1;
		} else if (quantifier == 1) {
			return 0;
		} else {
			throw new UnsupportedOperationException("unknown quantifier " + quantifier);
		}
	}

	/**
	 * @return false iff some subformula is a {@link QuantifiedFormula}.
	 */
	public static boolean isQuantifierFree(final Term term) {
		return !new SubtermPropertyChecker(x -> (x instanceof QuantifiedFormula)).isPropertySatisfied(term);
	}


}
