/* Copyright (C) 2015 Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * Copyright (C) 2015 University of Freiburg
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
package ultimate.blockencoding.encoding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import ultimate.icfgtransformer.transformulatransformers.NNF;
import ultimate.icfgtransformer.transformulatransformers.RemoveNegation;
import ultimate.icfgtransformer.transformulatransformers.RewriteDisequality;
import ultimate.icfgtransformer.transformulatransformers.RewriteIte;
import ultimate.icfgtransformer.transformulatransformers.TermException;
import ultimate.icfgtransformer.transformulatransformers.TransitionPreprocessor;
import ultimate.rcfgbuilder.cfg.Summary;
import ultimate.util.cfg.BasicIcfg;
import ultimate.util.cfg.CfgSmtToolkit;
import ultimate.util.cfg.IcfgUtils;
import ultimate.util.cfg.structure.IIcfgInternalTransition;
import ultimate.util.cfg.structure.IcfgEdge;
import ultimate.util.cfg.structure.IcfgEdgeIterator;
import ultimate.util.cfg.structure.IcfgLocation;
import ultimate.util.cfg.transformations.BlockEncodingBacktranslator;
import ultimate.util.cfg.transformations.ReplacementVarFactory;
import ultimate.util.cfg.transitions.ModifiableTransFormula;
import ultimate.util.cfg.transitions.ModifiableTransFormulaUtils;
import ultimate.util.cfg.transitions.TransFormulaBuilder;
import ultimate.util.smt.managedscript.ManagedScript;

/**
 *
 * @author Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 */
public final class RewriteNotEquals extends BaseBlockEncoder<IcfgLocation> {

	private final IcfgEdgeBuilder mEdgeBuilder;

	public RewriteNotEquals(final IcfgEdgeBuilder edgeBuilder, final BlockEncodingBacktranslator backtranslator,
			final Logger logger) {
		super(backtranslator, logger);
		mEdgeBuilder = edgeBuilder;
	}

	@Override
	protected BasicIcfg<IcfgLocation> createResult(final BasicIcfg<IcfgLocation> icfg) {
		final IcfgEdgeIterator iter = new IcfgEdgeIterator(icfg);
		final Set<IcfgEdge> toRemove = new HashSet<>();
		final CfgSmtToolkit toolkit = icfg.getCfgSmtToolkit();
		final ManagedScript mgScript = toolkit.getManagedScript();
		final ReplacementVarFactory repVarFac = new ReplacementVarFactory(toolkit, false, logger);
		final List<TransitionPreprocessor> transformer = new ArrayList<>();
		transformer.add(new RewriteIte(logger));
		transformer.add(new NNF(logger));
		transformer.add(new RemoveNegation());
		transformer.add(new RewriteDisequality());
		transformer.add(new NNF(logger));
		transformer.add(new RemoveNegation());

		while (iter.hasNext()) {
			final IcfgEdge edge = iter.next();
			if (!(edge instanceof IIcfgInternalTransition<?>) || edge instanceof Summary) {
				continue;
			}

			final ModifiableTransFormula mtf = ModifiableTransFormulaUtils
					.buildTransFormula(IcfgUtils.getTransformula(edge), repVarFac, mgScript, logger);
			final ModifiableTransFormula rewrittenMtf = rewrite(transformer, mtf, mgScript);
			if (mtf.getFormula().equals(rewrittenMtf.getFormula())) {
				// nothing to do
				continue;
			}
			logger.fine("Rewrote ");
			logger.fine(mtf.getFormula().toString());
			logger.fine(rewrittenMtf.getFormula().toString());
			if (!toRemove.add(edge)) {
				continue;
			}
			final IcfgEdge newEdge = mEdgeBuilder.constructInternalTransition(edge, edge.getSource(), edge.getTarget(),
					TransFormulaBuilder.constructCopy(mgScript, rewrittenMtf, Collections.emptySet(),
							Collections.emptySet(), Collections.emptyMap(), logger));
			rememberEdgeMapping(newEdge, edge);
		}

		if (!repVarFac.isUnused()) {
			final CfgSmtToolkit newToolkit = new CfgSmtToolkit(repVarFac.constructModifiableGlobalsTable(), mgScript,
					repVarFac.constructIIcfgSymbolTable(), toolkit.getProcedures(), toolkit.getInParams(),
					toolkit.getOutParams(), toolkit.getIcfgEdgeFactory(), toolkit.getConcurrencyInformation(),
					toolkit.getSmtSymbols(), logger);
			icfg.setCfgSmtToolkit(newToolkit);
		}

		toRemove.stream().forEach(a -> {
			a.disconnectSource();
			a.disconnectTarget();
		});
		mRemovedEdges = toRemove.size();
		return icfg;
	}

	private static ModifiableTransFormula rewrite(final List<TransitionPreprocessor> transformers,
			final ModifiableTransFormula mtf, final ManagedScript mgdScript) {
		ModifiableTransFormula rtr = mtf;
		for (final TransitionPreprocessor transformer : transformers) {
			try {
				rtr = transformer.process(mgdScript, rtr);
			} catch (final TermException e) {
				throw new RuntimeException(e);
			}
		}
		return rtr;
	}

	@Override
	public boolean isGraphStructureChanged() {
		return mRemovedEdges > 0;
	}
}
