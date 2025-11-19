/*
 * Copyright (C) 2014-2015 Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
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
import ultimate.models.IElement;
import ultimate.models.observers.IUnmanagedObserver;
import ultimate.util.cfg.BasicIcfg;
import ultimate.util.cfg.CfgSmtToolkit;
import ultimate.util.cfg.structure.IIcfg;
import ultimate.util.cfg.structure.IcfgLocation;
import ultimate.util.cfg.transformations.BlockEncodingBacktranslator;
import ultimate.util.cfg.transformations.IcfgDuplicator;
import ultimate.util.smt.SmtUtils.SimplificationTechnique;
import ultimate.util.smt.SmtUtils.XnfConversionTechnique;
import ultimate.blockencoding.encoding.IcfgEdgeBuilder;

/**
 *
 * @author Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 *
 */
public class BlockEncodingObserver implements IUnmanagedObserver {
	// Global Java logger
	private final Logger logger;

	private final Storage storage;

	private final BlockEncodingBacktranslator mBacktranslator;
	private final XnfConversionTechnique mXnfConversionTechnique;
	private final SimplificationTechnique mSimplificationTechnique;

	private IIcfg<IcfgLocation> mResult;

	public BlockEncodingObserver(final BlockEncodingBacktranslator backtranslator,
			final SimplificationTechnique simplTech, final XnfConversionTechnique xnfConvTech, final Logger logger,
			final Storage storage) {
		this.logger = logger;
		this.storage = storage;
		mBacktranslator = backtranslator;
		mSimplificationTechnique = simplTech;
		mXnfConversionTechnique = xnfConvTech;
	}

	@Override
	public void init() {
		// no initialization needed
	}

	@Override
	public void finish() throws Throwable {
		// not needed
	}

	@Override
	public boolean performedChanges() {
		return false;
	}

	public IIcfg<IcfgLocation> getResult() {
		return mResult;
	}

	@Override
	public boolean process(final IElement root) throws Exception {
		if (root instanceof IIcfg<?>) {
			final IIcfg<?> originalIcfg = (IIcfg<?>) root;
			final CfgSmtToolkit toolkit = originalIcfg.getCfgSmtToolkit();
			final IcfgEdgeBuilder edgeBuilder = new IcfgEdgeBuilder(toolkit, mSimplificationTechnique,
					mXnfConversionTechnique, logger, storage);
			final BasicIcfg<IcfgLocation> copiedIcfg = new IcfgDuplicator(toolkit.getManagedScript(), mBacktranslator,
					logger)
					.copy(originalIcfg);
			mResult = new BlockEncoder(mBacktranslator, edgeBuilder, copiedIcfg, logger, storage).getResult();
			return false;
		}
		return true;
	}
}
