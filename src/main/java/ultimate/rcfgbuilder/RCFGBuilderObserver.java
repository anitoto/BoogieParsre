/*
 * Copyright (C) 2014-2015 Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * Copyright (C) 2010-2015 Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
 * Copyright (C) 2015 University of Freiburg
 *
 * This file is part of the ULTIMATE RCFGBuilder plug-in.
 *
 * The ULTIMATE RCFGBuilder plug-in is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ULTIMATE RCFGBuilder plug-in is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ULTIMATE RCFGBuilder plug-in. If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional permission under GNU GPL version 3 section 7:
 * If you modify the ULTIMATE RCFGBuilder plug-in, or any covered work, by linking
 * or combining it with Eclipse RCP (or a modified version of Eclipse RCP),
 * containing parts covered by the terms of the Eclipse Public License, the
 * licensors of the ULTIMATE RCFGBuilder plug-in grant you additional permission
 * to convey the resulting work.
 */
package ultimate.rcfgbuilder;

import java.io.IOException;
import java.util.logging.Logger;

import ultimate.Storage;
import ultimate.ast.Unit;
import ultimate.models.IElement;
import ultimate.models.observers.IUnmanagedObserver;
import ultimate.logic.SMTLIBException;
import ultimate.rcfgbuilder.cfg.BoogieIcfgLocation;
import ultimate.rcfgbuilder.cfg.CfgBuilder;
import ultimate.util.cfg.IcfgUtils;
import ultimate.util.cfg.structure.IIcfg;

/**
 * @author TOTO (Towzer)
 */
public class RCFGBuilderObserver implements IUnmanagedObserver {

	/**
	 * Root Node of this Ultimate model. I use this to store information that should
	 * be passed to the next plugin. The
	 * Sucessors of this node exactly the initial nodes of procedures.
	 */
	// Global Java logger
	private final Logger logger;

	private final Storage storage;

	private CfgBuilder recCFGBuilder;
	private IIcfg<BoogieIcfgLocation> mGraphroot;

	public RCFGBuilderObserver(final Logger logger, final Storage storage) {
		this.logger = logger;
		this.storage = storage;
	}

	/**
	 *
	 * @return the root of the CFG.
	 */
	public IIcfg<BoogieIcfgLocation> getRoot() {
		return mGraphroot;
	}

	@Override
	public void init() {
		// not needed
	}

	/**
	 * Copied from CFG Builder
	 *
	 * Called by the Ultimate Framework. Finds all procedure declarations and checks
	 * whether they're implementations or
	 * just declarations. If implementation is present calls makeProcedureCFG() and
	 * appends CFG as child of procedure
	 * node to CFG
	 *
	 * @throws IOException
	 */
	@Override
	public boolean process(final IElement root) throws IOException {
		if (!(root instanceof Unit)) {
			logger.info("No WrapperNode. Let Ultimate process with next node");
			return true;
		}
		final Unit unit = (Unit) root;
		recCFGBuilder = new CfgBuilder(unit, logger, storage);
		try {
			mGraphroot = recCFGBuilder.createIcfg(unit);
			if (IcfgUtils.hasUnreachableProgramPoints(mGraphroot)) {
				throw new AssertionError("ICFG has unreachable program points");
			}
		} catch (final SMTLIBException e) {
			final String message = e.getMessage();
			if ("Cannot create quantifier in quantifier-free logic".equals(message)
					|| "Sort Array not declared".equals(message)
					|| "Unsupported non-linear arithmetic".equals(message)) {
				logger.warning("Unsupported syntax: " + e.getMessage());
			} else {
				throw e;
			}
		}
		return false;
	}

	/**
	 * Terminate MonitoredProcess used in {@link CfgBuilder}
	*/
	@Override
	public void finish() {
		recCFGBuilder.exit();
	}

	@Override
	public boolean performedChanges() {
		return false;
	}
}
