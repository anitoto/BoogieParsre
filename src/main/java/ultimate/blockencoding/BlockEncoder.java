/*
 * Copyright (C) 2017 Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * Copyright (C) 2017 University of Freiburg
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Logger;

import towzer.preferences.AppConfig;

import ultimate.Storage;
import ultimate.blockencoding.BlockEncodingPreferences.MinimizeStates;
import ultimate.blockencoding.encoding.IEncoder;
import ultimate.blockencoding.encoding.IcfgEdgeBuilder;
import ultimate.blockencoding.encoding.InterproceduralSequenzer;
import ultimate.blockencoding.encoding.MaximizeFinalStates;
import ultimate.blockencoding.encoding.MinimizeStatesMultiEdgeMultiNode;
import ultimate.blockencoding.encoding.MinimizeStatesMultiEdgeSingleNode;
import ultimate.blockencoding.encoding.MinimizeStatesSingleEdgeSingleNode;
import ultimate.blockencoding.encoding.ParallelComposer;
import ultimate.blockencoding.encoding.RemoveInfeasibleEdges;
import ultimate.blockencoding.encoding.RemoveSinkStates;
import ultimate.blockencoding.encoding.RewriteNotEquals;
import ultimate.blockencoding.encoding.Simplifier;
import ultimate.blockencoding.encoding.SmallBlockEncoder;
import ultimate.logic.Term;
import ultimate.models.annotation.BuchiProgramAcceptingStateAnnotation;
import ultimate.rcfgbuilder.cfg.BoogieIcfgLocation;
import ultimate.rcfgbuilder.util.IcfgSizeBenchmark;
import ultimate.util.cfg.BasicIcfg;
import ultimate.util.cfg.CfgSmtToolkit;
import ultimate.util.cfg.structure.IIcfg;
import ultimate.util.cfg.structure.IcfgEdge;
import ultimate.util.cfg.structure.IcfgLocation;
import ultimate.util.cfg.transformations.BlockEncodingBacktranslator;
import ultimate.util.cfg.transformations.IcfgDuplicator;
import ultimate.util.cfg.transitions.TransFormula;
import ultimate.util.smt.SmtUtils.SimplificationTechnique;
import ultimate.util.smt.SmtUtils.XnfConversionTechnique;

/**
 *
 * The {@link BlockEncoder} provides different kinds of transformations for
 * {@link IIcfg}s. These transformations
 * transcode the {@link TransFormula}s and the structure of an {@link IIcfg}
 * s.t. you may have more or less edges and
 * nodes.
 *
 * @author Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 *
 */
public final class BlockEncoder {

	private static final BuchiProgramAcceptingStateAnnotation BUCHI_PROGRAM_ACCEPTING_STATE_ANNOTATION = new BuchiProgramAcceptingStateAnnotation();

	// Global Java logger
	private final Logger logger;

	private final Storage storage;

	private final BlockEncodingBacktranslator mBacktranslator;
	private final IcfgEdgeBuilder mEdgeBuilder;

	private BasicIcfg<IcfgLocation> mIterationResult;

	/**
	 * Create a {@link BlockEncoder} for the BlockEncoding plugin. This constructor
	 * modifies the supplied Icfg.
	 *
	 * @param logger
	 *                       The logger to use.
	 * @param services
	 *                       A {@link IUltimateServiceProvider} instance.
	 * @param backtranslator
	 *                       A backtranslator that is already registered with the
	 *                       toolchain
	 * @param builder
	 *                       An edge builder
	 * @param icfg
	 *                       An icfg
	 */
	public BlockEncoder(final BlockEncodingBacktranslator backtranslator, final IcfgEdgeBuilder builder,
			final BasicIcfg<IcfgLocation> icfg, final Logger logger, final Storage storage) {
		this.logger = logger;
		this.storage = storage;
		mIterationResult = null;
		mBacktranslator = backtranslator;
		mEdgeBuilder = builder;
		processIcfg(icfg);
	}

	/**
	 * Create a {@link BlockEncoder} instance for usage from anywhere. Does not
	 * register its backtranslation and does
	 * create the necessary data structures for itself. In particular, does not
	 * modify the supplied Icfg.
	 *
	 * @param logger
	 *                                The logger we should use.
	 * @param services
	 *                                A {@link IUltimateServiceProvider} instance
	 *                                that is used to get the preferences (use
	 *                                {@link IUltimateServiceProvider#registerPreferenceLayer(Class, String...)
	 *                                and
	 *                                BlockEncodingPreferences}.
	 * @param originalIcfg
	 *                                The {@link IIcfg} you wish to encode.
	 * @param simplificationTechnique
	 *                                The {@link SimplificationTechnique} that
	 *                                should be used.
	 * @param xnfConversionTechnique
	 *                                The {@link XnfConversionTechnique} that should
	 *                                be used.
	 */
	public BlockEncoder(final IIcfg<?> originalIcfg, final SimplificationTechnique simplificationTechnique,
			final XnfConversionTechnique xnfConversionTechnique, final Logger logger, final Storage storage) {
		this.logger = logger;
		this.storage = storage;
		mBacktranslator = new BlockEncodingBacktranslator(IcfgEdge.class, Term.class, logger);
		final CfgSmtToolkit toolkit = originalIcfg.getCfgSmtToolkit();
		mEdgeBuilder = new IcfgEdgeBuilder(toolkit, simplificationTechnique, xnfConversionTechnique, logger, storage);
		final BasicIcfg<IcfgLocation> copiedIcfg = new IcfgDuplicator(toolkit.getManagedScript(), mBacktranslator,
				logger).copy(originalIcfg);
		processIcfg(copiedIcfg);
	}

	public IIcfg<IcfgLocation> getResult() {
		return mIterationResult;
	}

	public BlockEncodingBacktranslator getBacktranslator() {
		return mBacktranslator;
	}

	private void processIcfg(final BasicIcfg<IcfgLocation> node) {
		// measure size of rcfg
		reportSizeBenchmark("Initial Icfg", node);

		int maxIters = AppConfig.fxp_max_iterations.get() - 1;
		if (maxIters < 0) {
			maxIters = -1;
		} else {
			logger.info(String.format("Using %s=%s", AppConfig.LABEL_FXP_MAX_ITERATIONS, maxIters + 1));
		}

		mIterationResult = node;
		final List<Supplier<IEncoder<IcfgLocation>>> encoderProviders = getEncoderProviders(node);
		final boolean optimizeUntilFixpoint = AppConfig.fxp_until_fixpoint.get();
		logger.info(String.format("Using %s=%s", AppConfig.LABEL_FXP_UNTIL_FIXPOINT, optimizeUntilFixpoint));
		int i = 1;

		logger.fine("==== BE Preprocessing ====");

		if (AppConfig.pre_rewritenotequals.get()) {
			logger.info("Using " + AppConfig.LABEL_PRE_REWRITENOTEQUALS);
			mIterationResult = new RewriteNotEquals(mEdgeBuilder, mBacktranslator, logger).getResult(mIterationResult);
		}

		if (AppConfig.pre_sbe.get()) {
			logger.info("Using " + AppConfig.LABEL_PRE_SBE);
			int removedEdges = 0;
			while (true) {
				final SmallBlockEncoder sbe = new SmallBlockEncoder(mEdgeBuilder, mBacktranslator, logger, storage);
				mIterationResult = sbe.getResult(mIterationResult);
				removedEdges += sbe.getRemovedEdges();
				if (!sbe.isGraphStructureChanged()) {
					break;
				}
				if (!storage.getProgressMonitorService().continueProcessing()) {
					throw new RuntimeException("Timeout during SBE block encoding");
				}
			}
			logger.info("SBE split " + removedEdges + " edges");

		}

		while (true) {
			logger.fine("==== BE Pass #" + i + "====");
			++i;
			EncodingResult currentResult = new EncodingResult(mIterationResult, false);

			for (final Supplier<IEncoder<IcfgLocation>> provider : encoderProviders) {
				final IEncoder<IcfgLocation> encoder = provider.get();
				currentResult = applyEncoder(currentResult, encoder);
			}

			mIterationResult = currentResult.getIcfg();

			if (!storage.getProgressMonitorService().continueProcessing()) {
				throw new RuntimeException("Timeout during block encoding");
			}

			if (!optimizeUntilFixpoint || !currentResult.isChanged() || maxIters == 0) {
				break;
			}
			if (maxIters > 0) {
				maxIters--;
			}
		}

		logger.fine("==== BE Postprocessing ====");

		if (AppConfig.post_use_parallel_composition.get()) {
			logger.info(String.format("Using %s", AppConfig.LABEL_POST_USE_PARALLEL_COMPOSITION));
			mIterationResult = new ParallelComposer(mEdgeBuilder, mBacktranslator, logger).getResult(mIterationResult);
		}

		if (AppConfig.post_simplify_transitions.get()) {
			logger.info(String.format("Using %s", AppConfig.LABEL_POST_SIMPLIFY_TRANSITIONS));
			mIterationResult = new Simplifier(mEdgeBuilder, mBacktranslator, logger).getResult(mIterationResult);
		}

		reportSizeBenchmark("Encoded RCFG", mIterationResult);
	}

	private List<Supplier<IEncoder<IcfgLocation>>> getEncoderProviders(final IIcfg<?> icfg) {
		final List<Supplier<IEncoder<IcfgLocation>>> rtr = new ArrayList<>();

		// note that the order is important

		if (AppConfig.fxp_remove_infeasible_edges.get()) {
			logger.info("Using " + AppConfig.LABEL_FXP_REMOVE_INFEASIBLE_EDGES);
			rtr.add(() -> new RemoveInfeasibleEdges(mBacktranslator, logger));
		}

		if (AppConfig.fxp_maximize_final_states.get()) {
			logger.info("Using " + AppConfig.LABEL_FXP_MAXIMIZE_FINAL_STATES);
			rtr.add(() -> new MaximizeFinalStates(BlockEncoder::markBuchiProgramAccepting,
					BlockEncoder::isBuchiProgramAccepting, mBacktranslator, logger));
		}

		final boolean ignoreBlowup = AppConfig.fxp_minimize_states_ignore_blowup.get();
		logger.info(
				String.format("Using %s=%s", AppConfig.LABEL_FXP_MINIMIZE_STATES_IGNORE_BLOWUP, ignoreBlowup));

		final MinimizeStates minimizeStates = AppConfig.fxp_minimize_states.get();
		if (minimizeStates != MinimizeStates.NONE) {
			logger.info(String.format("Using %s=%s", AppConfig.LABEL_FXP_MINIMIZE_STATES, minimizeStates));
			switch (minimizeStates) {
				case SINGLE:
					rtr.add(() -> new MinimizeStatesSingleEdgeSingleNode(mEdgeBuilder, mBacktranslator,
							BlockEncoder::hasToBePreserved, ignoreBlowup, logger, storage));
					break;
				case SINGLE_NODE_MULTI_EDGE:
					rtr.add(() -> new MinimizeStatesMultiEdgeSingleNode(mEdgeBuilder, mBacktranslator,
							BlockEncoder::hasToBePreserved, ignoreBlowup, logger, storage));
					break;
				case MULTI:
					rtr.add(() -> new MinimizeStatesMultiEdgeMultiNode(mEdgeBuilder, mBacktranslator,
							BlockEncoder::hasToBePreserved, ignoreBlowup, logger, storage));
					break;
				default:
					throw new IllegalArgumentException(minimizeStates + " is an unknown enum value!");
			}
		}

		if (AppConfig.fxp_remove_sink_states.get()) {
			logger.info("Using " + AppConfig.LABEL_FXP_REMOVE_SINK_STATES);
			rtr.add(() -> new RemoveSinkStates(BlockEncoder::hasToBePreserved, mBacktranslator, logger));
		}

		rtr.add(() -> new InterproceduralSequenzer(mEdgeBuilder, mBacktranslator, logger));

		return rtr;
	}

	private static EncodingResult applyEncoder(final EncodingResult previousResult,
			final IEncoder<IcfgLocation> encoder) {
		final BasicIcfg<IcfgLocation> result = encoder.getResult(previousResult.getIcfg());
		return new EncodingResult(result, previousResult.isChanged() || encoder.isGraphStructureChanged());
	}

	private void reportSizeBenchmark(final String message, final IIcfg<?> root) {
		final IcfgSizeBenchmark bench = new IcfgSizeBenchmark(root, message);
		logger.info(message + " " + bench);
	}

	private static boolean hasToBePreserved(final IIcfg<?> icfg, final IcfgLocation node) {
		if (node == null) {
			return false;
		}
		if (node instanceof BoogieIcfgLocation) {
			final BoogieIcfgLocation pp = (BoogieIcfgLocation) node;
			return pp.isErrorLocation();
		}

		final String proc = node.getProcedure();
		final Set<?> errorNodes = icfg.getProcedureErrorNodes().get(proc);
		if (errorNodes != null && !errorNodes.isEmpty()) {
			return errorNodes.contains(node);
		}

		return false;
	}

	private static boolean isBuchiProgramAccepting(final IcfgLocation node) {
		return BuchiProgramAcceptingStateAnnotation.getAnnotation(node) != null;
	}

	private static void markBuchiProgramAccepting(final IcfgLocation node) {
		BUCHI_PROGRAM_ACCEPTING_STATE_ANNOTATION.annotate(node);
	}

	private static class EncodingResult {
		private final BasicIcfg<IcfgLocation> mNode;
		private final boolean mIsChanged;

		private EncodingResult(final BasicIcfg<IcfgLocation> node, final boolean isChanged) {
			mNode = node;
			mIsChanged = isChanged;
		}

		private boolean isChanged() {
			return mIsChanged;
		}

		private BasicIcfg<IcfgLocation> getIcfg() {
			return mNode;
		}
	}

}
