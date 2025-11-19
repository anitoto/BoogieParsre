/*
 * Copyright (C) 2012-2015 University of Freiburg
 *
 * This file is part of the ULTIMATE Model Checker Utils Library.
 *
 * The ULTIMATE Model Checker Utils Library is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * The ULTIMATE Model Checker Utils Library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ULTIMATE Model Checker Utils Library. If not,
 * see <http://www.gnu.org/licenses/>.
 *
 * Additional permission under GNU GPL version 3 section 7:
 * If you modify the ULTIMATE Model Checker Utils Library, or any covered work,
 * by linking or combining it with Eclipse RCP (or a modified version of
 * Eclipse RCP), containing parts covered by the terms of the Eclipse Public
 * License, the licensors of the ULTIMATE Model Checker Utils Library grant you
 * additional permission to convey the resulting work.
 */
package ultimate.util.smt;

import java.util.logging.Logger;

import ultimate.Storage;
import ultimate.logic.Logics;
import ultimate.logic.SMTLIBException;
import ultimate.logic.Script;
import ultimate.logic.Term;
import ultimate.logic.simplification.SimplifyDDA;
import ultimate.util.smt.SolverBuilder.SolverSettings;

/**
 * Variant of {@link SimplifyDDA} that uses SMTInterpol's "quick check". The "quick check" is much faster but returns
 * UNKNOWN much more often. Here, we always start a new instance of SMTInterpol. The input is transferred to this new
 * instance such that each non-boolean subterm is replaced by a fresh boolean constant. This ensures that SMTInterpol is
 * able to handle the term.
 *
 * @author Matthias Heizmann
 * @author TOTO (Towzer)
 */
public class SimplifyQuick {
	// Global Java logger
	private final Logger logger;

	private final Storage storage;

	private final Script mScript;
	private static final int TIMOUT_IN_SECONDS = 10;

	public SimplifyQuick(final Script script, final Logger logger, final Storage storage) {
		this.logger = logger;
		this.storage = storage;
		mScript = script;
	}

	public Term getSimplifiedTerm(final Term inputTerm) throws SMTLIBException {

		final SolverSettings settings =
				new SolverBuilder.SolverSettings(false, false, "", TIMOUT_IN_SECONDS * 1000, null, false, null, null);
		final Script simplificationScript = SolverBuilder.buildScript(settings, logger, storage);
		simplificationScript.setLogic(Logics.CORE);
		final TermTransferrer towards = new TermTransferrerBooleanCore(simplificationScript, logger);
		final Term foreign = towards.transform(inputTerm);

		simplificationScript.setOption(":check-type", "QUICK");
		final SimplifyDDAWithTimeout dda = new SimplifyDDAWithTimeout(simplificationScript, false, logger, storage);
		final Term foreignsimplified = dda.getSimplifiedTerm(foreign);
		// simplificationScript.setOption(":check-type", "FULL");

		final TermTransferrer back = new TermTransferrer(mScript, towards.getBacktranferMapping(), false, logger);
		final Term simplified = back.transform(foreignsimplified);
		simplificationScript.exit();

		return simplified;
	}
}
