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
package ultimate.util.smt.predicates;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import ultimate.Storage;
import ultimate.logic.Script;
import ultimate.logic.Term;
import ultimate.logic.TermVariable;
import ultimate.util.cfg.transitions.TransFormula;
import ultimate.util.smt.SmtUtils;
import ultimate.util.smt.SubstitutionWithLocalSimplification;
import ultimate.util.smt.linearterms.QuantifierPusher;
import ultimate.util.smt.linearterms.QuantifierPusher.PqeTechniques;
import ultimate.util.smt.managedscript.ManagedScript;

/**
 * Term domain operations that are needed for {@link PredicateTransformer}
 * 
 * @author Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
 * @author TOTO (Towzer)
 *
 */
public class TermDomainOperationProvider implements IDomainSpecificOperationProvider<Term, IPredicate, TransFormula> {
	// Global Java logger
	private final Logger logger;

	private final Storage storage;

	private final ManagedScript mMgdScript;

	public TermDomainOperationProvider(final ManagedScript mgdScript, final Logger logger, final Storage storage) {
		this.logger = logger;
		this.storage = storage;
		mMgdScript = mgdScript;
	}

	@Override
	public Term getConstraint(final IPredicate p) {
		return p.getFormula();
	}

	@Override
	public boolean isConstraintUnsatisfiable(final Term constraint) {
		return SmtUtils.isFalse(constraint);
	}

	@Override
	public boolean isConstraintValid(final Term constraint) {
		return SmtUtils.isTrue(constraint);
	}

	@Override
	public Term getConstraintFromTransitionRelation(final TransFormula tf) {
		return tf.getFormula();
	}

	@Override
	public Term renameVariables(final Map<Term, Term> substitutionForTransFormula, final Term constraint) {
		final Term renamedTransFormula = new SubstitutionWithLocalSimplification(mMgdScript,
				substitutionForTransFormula, logger).transform(constraint);
		return renamedTransFormula;
	}

	@Override
	public Term constructConjunction(final List<Term> conjuncts) {
		return SmtUtils.and(mMgdScript.getScript(), conjuncts);
	}

	@Override
	public Term constructDisjunction(final List<Term> disjuncts) {
		return SmtUtils.or(mMgdScript.getScript(), disjuncts);
	}

	@Override
	public Term constructNegation(final Term operand) {
		return SmtUtils.not(mMgdScript.getScript(), operand, logger);
	}

	@Override
	public Term projectExistentially(final Set<TermVariable> varsToProjectAway, final Term constraint) {
		return constructQuantifiedFormula(Script.EXISTS, varsToProjectAway, constraint);
	}

	@Override
	public Term projectUniversally(final Set<TermVariable> varsToProjectAway, final Term constraint) {
		return constructQuantifiedFormula(Script.FORALL, varsToProjectAway, constraint);
	}

	private Term constructQuantifiedFormula(final int quantifier, final Set<TermVariable> varsToQuantify,
			final Term term) {
		final Term quantified = SmtUtils.quantifier(mMgdScript.getScript(), quantifier, varsToQuantify, term);
		final Term pushed = new QuantifierPusher(mMgdScript, false, PqeTechniques.ONLY_DER, logger, storage)
				.transform(quantified);
		return pushed;
	}

}
