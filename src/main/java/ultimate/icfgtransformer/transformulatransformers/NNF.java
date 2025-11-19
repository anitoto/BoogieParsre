/*
 * Copyright (C) 2014-2015 Jan Leike (leike@informatik.uni-freiburg.de)
 * Copyright (C) 2014-2015 Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
 * Copyright (C) 2012-2015 University of Freiburg
 * 
 * This file is part of the ULTIMATE IcfgTransformer library.
 * 
 * The ULTIMATE IcfgTransformer library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * The ULTIMATE IcfgTransformer library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ULTIMATE IcfgTransformer library. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Additional permission under GNU GPL version 3 section 7:
 * If you modify the ULTIMATE IcfgTransformer library, or any covered work, by linking
 * or combining it with Eclipse RCP (or a modified version of Eclipse RCP),
 * containing parts covered by the terms of the Eclipse Public License, the
 * licensors of the ULTIMATE IcfgTransformer library grant you additional permission
 * to convey the resulting work.
 */
package ultimate.icfgtransformer.transformulatransformers;

import java.util.logging.Logger;

import ultimate.logic.Script;
import ultimate.logic.Script.LBool;
import ultimate.logic.Term;
import ultimate.logic.Util;
import ultimate.util.cfg.transitions.ModifiableTransFormula;
import ultimate.util.smt.SmtUtils;
import ultimate.util.smt.managedscript.ManagedScript;

/**
 * Convert a formula into negation normal form.
 * 
 * @author Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 */
public class NNF extends TransitionPreprocessor {
	public static final String DESCRIPTION = "Transform into negation normal form";

	// Global Java logger
	private final Logger logger;

	public NNF(final Logger logger) {
		super();
		this.logger = logger;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public boolean checkSoundness(final Script script, final ModifiableTransFormula oldTF,
			final ModifiableTransFormula newTF) {
		final Term oldTerm = oldTF.getFormula();
		final Term newTerm = newTF.getFormula();
		return LBool.SAT != Util.checkSat(script, script.term("distinct", oldTerm, newTerm));
	}

	@Override
	public ModifiableTransFormula process(final ManagedScript script, final ModifiableTransFormula tf)
			throws TermException {
		final Term dnf = SmtUtils.toNnf(script, tf.getFormula(), logger);
		tf.setFormula(dnf);
		return tf;
	}
}
