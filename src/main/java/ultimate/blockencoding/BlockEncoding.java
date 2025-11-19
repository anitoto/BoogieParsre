/*
 * Copyright (C) 2013-2015 Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * Copyright (C) 2015 University of Freiburg
 * Copyright (C) 2013-2015 Vincent Langenfeld (langenfv@informatik.uni-freiburg.de)
 *
 * This file is part of the ULTIMATE BlockEncodingV2 plug-in.
 *
 * The ULTIMATE BlockEncodingV2 plug-in is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ULTIMATE BlockEncodingV2 plug-in is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ULTIMATE BlockEncodingV2 plug-in. If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional permission under GNU GPL version 3 section 7:
 * If you modify the ULTIMATE BlockEncodingV2 plug-in, or any covered work, by linking
 * or combining it with Eclipse RCP (or a modified version of Eclipse RCP),
 * containing parts covered by the terms of the Eclipse Public License, the
 * licensors of the ULTIMATE BlockEncodingV2 plug-in grant you additional permission
 * to convey the resulting work.
 */
package ultimate.blockencoding;

import java.util.logging.Logger;

import ultimate.Storage;
import ultimate.logic.Term;
import ultimate.util.cfg.structure.IcfgEdge;
import ultimate.util.cfg.structure.IcfgLocation;
import ultimate.util.cfg.structure.IIcfg;
import ultimate.util.cfg.transformations.BlockEncodingBacktranslator;
import ultimate.util.smt.SmtUtils.SimplificationTechnique;
import ultimate.util.smt.SmtUtils.XnfConversionTechnique;

/**
 * @author Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * @author TOTO (Towzer)
 */
public class BlockEncoding {
	// Global Java logger
	private final Logger logger;

	private final IIcfg<?> icfg;
	private BlockEncodingObserver blockEncodingObs;

	private static final SimplificationTechnique SIMPLIFICATION_TECHNIQUE = SimplificationTechnique.SIMPLIFY_DDA;
	private static final XnfConversionTechnique XNF_CONVERSION_TECHNIQUE = XnfConversionTechnique.BOTTOM_UP_WITH_LOCAL_SIMPLIFICATION;

	private BlockEncodingBacktranslator backtranslator;

	public BlockEncoding(final Logger logger, final Storage storage, final IIcfg<?> icfg) {
		this.logger = logger;
		this.icfg = icfg;
		backtranslator = new BlockEncodingBacktranslator(IcfgEdge.class, Term.class, logger);
		blockEncodingObs = new BlockEncodingObserver(backtranslator, SIMPLIFICATION_TECHNIQUE,
				XNF_CONVERSION_TECHNIQUE, logger, storage);
	}

	public void init() {
		// not needed
	}

	public void finish() {
		// not needed
	}

	public IIcfg<IcfgLocation> executeObservers() {
		logger.info("Executing the observer " + blockEncodingObs.getClass().getSimpleName());
		try {
			blockEncodingObs.process(icfg);
		} catch (Exception e) {
			logger.severe("Failed to execute the observer " + blockEncodingObs.getClass().getSimpleName() + ": "
					+ e.getMessage());
		}
		return blockEncodingObs.getResult();
	}
}
