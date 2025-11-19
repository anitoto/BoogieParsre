/*
 * Copyright (C) 2014-2015 Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * Copyright (C) 2012-2015 Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
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
package ultimate.rcfgbuilder.cfg;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import towzer.preferences.AppConfig;

import ultimate.Storage;
import ultimate.models.ModelUtils;
import ultimate.models.annotation.Visualizable;
import ultimate.util.cfg.CfgSmtToolkit;
import ultimate.util.cfg.structure.IAction;
import ultimate.util.cfg.structure.ICallAction;
import ultimate.util.cfg.structure.IIcfgInternalTransition;
import ultimate.util.cfg.structure.IReturnAction;
import ultimate.util.cfg.structure.IcfgLocation;
import ultimate.util.cfg.transitions.TransFormulaUtils;
import ultimate.util.cfg.transitions.UnmodifiableTransFormula;
import ultimate.util.cfg.variables.IProgramNonOldVar;
import ultimate.util.smt.SmtUtils.SimplificationTechnique;
import ultimate.util.smt.SmtUtils.XnfConversionTechnique;

/**
 * Edge in a recursive control flow graph that represents a sequence of
 * CodeBlocks which are executed one after the
 * other if this edge is executed.
 *
 * @author Matthias Heizmann
 * @author Daniel Dietsch (dietsch@informatik.unirfreiburg.de)
 */
public class SequentialComposition extends CodeBlock implements IIcfgInternalTransition<IcfgLocation> {

	private static final long serialVersionUID = 9192152338120598669L;
	private final List<CodeBlock> mCodeBlocks;
	private String mPrettyPrinted;
	private final int mCallsWithoutReturns;
	private final XnfConversionTechnique xnfConversionTechnique;
	private final SimplificationTechnique simplificationTechnique;

	SequentialComposition(final int serialNumber, final BoogieIcfgLocation source, final BoogieIcfgLocation target,
			final CfgSmtToolkit csToolkit, final boolean simplify, final boolean extPqe,
			final List<CodeBlock> codeBlocks, final XnfConversionTechnique xnfConversionTechnique,
			final SimplificationTechnique simplificationTechnique, final Logger logger, final Storage storage) {
		super(serialNumber, source, target, logger);

		mCodeBlocks = codeBlocks;
		mCallsWithoutReturns = getCheckedOpenCalls(codeBlocks).size();
		mPrettyPrinted = null;
		this.xnfConversionTechnique = xnfConversionTechnique;
		this.simplificationTechnique = simplificationTechnique;

		for (final CodeBlock currentCodeblock : codeBlocks) {
			currentCodeblock.disconnectSource();
			currentCodeblock.disconnectTarget();
			ModelUtils.copyAnnotations(currentCodeblock, this);
		}

		// workaround: set annotation with this pluginId again, because it was
		// overwritten by the mergeAnnotations method
		getPayload().getAnnotations().put("RCFGBuilder", mAnnotation);

		final boolean transformToCNF = AppConfig.cnf.get();

		mTransitionFormula = getInterproceduralTransFormula(csToolkit, simplify, extPqe, transformToCNF, false,
				codeBlocks, xnfConversionTechnique, simplificationTechnique, logger, storage);
		mTransitionFormulaWithBranchEncoders = getInterproceduralTransFormula(csToolkit, simplify, extPqe,
				transformToCNF, true, codeBlocks, xnfConversionTechnique, simplificationTechnique, logger, storage);
	}

	public XnfConversionTechnique getXnfConversionTechnique() {
		return this.xnfConversionTechnique;
	}

	public SimplificationTechnique getSimplificationTechnique() {
		return this.simplificationTechnique;
	}

	private Deque<Call> getCheckedOpenCalls(final List<CodeBlock> codeBlocks) {
		// TODO: Necessary in runtime code?
		final Deque<Call> callstack = new ArrayDeque<>();
		for (final CodeBlock currentCodeblock : codeBlocks) {
			if (currentCodeblock instanceof Call) {
				callstack.addFirst((Call) currentCodeblock);
			} else if (currentCodeblock instanceof Return) {
				final Return currentReturn = (Return) currentCodeblock;
				if (callstack.isEmpty()) {
					throw new IllegalArgumentException("Call/Return order violated");
				}
				final Call lastCall = callstack.removeFirst();
				if (!Objects.equals(currentReturn.getCallStatement(), lastCall.getCallStatement())) {
					throw new IllegalArgumentException("Call/Return order violated");
				}
			} else if (currentCodeblock.getNumberOfOpenCalls() != 0) {
				if (currentCodeblock instanceof SequentialComposition) {
					final SequentialComposition seqComp = (SequentialComposition) currentCodeblock;
					final Deque<Call> innerCallstack = getCheckedOpenCalls(seqComp.getCodeBlocks());
					callstack.addAll(innerCallstack);
				} else {
					throw new IllegalArgumentException("Open calls are only supported in sequential compositions");
				}
			}
		}
		return callstack;
	}

	@Override
	public String getPrettyPrintedStatements() {
		if (mPrettyPrinted == null) {
			final StringBuilder sb = new StringBuilder();
			for (final CodeBlock block : mCodeBlocks) {
				sb.append(block.getPrettyPrintedStatements());
			}
			mPrettyPrinted = sb.toString();
		}
		return mPrettyPrinted;
	}

	@Visualizable
	public List<CodeBlock> getCodeBlocks() {
		return mCodeBlocks;
	}

	@Override
	public void setTransitionFormula(final UnmodifiableTransFormula transFormula) {
		throw new UnsupportedOperationException("transition formula is computed in constructor");
	}

	/**
	 * Returns Transformula for a sequence of CodeBlocks that may (opposed to the
	 * method sequentialComposition) contain
	 * also Call and Return.
	 */
	public static <ACTION extends IAction> UnmodifiableTransFormula getInterproceduralTransFormula(
			final CfgSmtToolkit csToolkit, final boolean simplify, final boolean extPqe, final boolean tranformToCNF,
			final boolean withBranchEncoders, final List<ACTION> actions,
			final XnfConversionTechnique xnfConversionTechnique, final SimplificationTechnique simplificationTechnique,
			final Logger logger, final Storage storage) {
		return getInterproceduralTransFormula(csToolkit, simplify, extPqe, tranformToCNF, withBranchEncoders, null,
				null, null, null, actions, xnfConversionTechnique, simplificationTechnique, logger, storage);
	}

	private static <ACTION extends IAction> UnmodifiableTransFormula getInterproceduralTransFormula(
			final CfgSmtToolkit csToolkit, final boolean simplify, final boolean extPqe, final boolean tranformToCNF,
			final boolean withBranchEncoders, final String nameStartProcedure,
			final UnmodifiableTransFormula[] beforeCall, final ICallAction call, final IReturnAction ret,
			final List<ACTION> actions, final XnfConversionTechnique xnfConversionTechnique,
			final SimplificationTechnique simplificationTechnique, final Logger logger, final Storage storage) {
		final List<UnmodifiableTransFormula> beforeFirstPendingCall = new ArrayList<>();
		ICallAction lastUnmatchedCall = null;
		int callsSinceLastUnmatchedCall = 0;
		int returnsSinceLastUnmatchedCall = 0;
		List<ACTION> afterLastUnmatchedCall = new ArrayList<>();
		for (int i = 0; i < actions.size(); i++) {
			final ACTION currentAction = actions.get(i);
			if (lastUnmatchedCall == null) {
				if (currentAction instanceof ICallAction) {
					lastUnmatchedCall = (ICallAction) currentAction;
				} else {
					assert !(currentAction instanceof IReturnAction);
					if (withBranchEncoders) {
						// TODO: What about branchencoders in the IAction context?
						if (currentAction instanceof CodeBlock) {
							beforeFirstPendingCall
									.add(((CodeBlock) currentAction).getTransitionFormulaWithBranchEncoders());
						} else {
							// things that are no codeblock cannot have branch encoders
							throw new UnsupportedOperationException("No codeblock, no branch encoders");
						}
					} else {
						beforeFirstPendingCall.add(currentAction.getTransformula());
					}
				}
			} else {
				if (currentAction instanceof IReturnAction) {
					if (callsSinceLastUnmatchedCall == returnsSinceLastUnmatchedCall) {
						final IReturnAction correspondingReturn = (IReturnAction) currentAction;
						final List<IAction> actionsBetween = new ArrayList<>(afterLastUnmatchedCall);
						final UnmodifiableTransFormula localTransFormula = getInterproceduralTransFormula(csToolkit,
								simplify, extPqe, tranformToCNF, withBranchEncoders, null, null, lastUnmatchedCall,
								correspondingReturn, actionsBetween, xnfConversionTechnique, simplificationTechnique,
								logger, storage);
						beforeFirstPendingCall.add(localTransFormula);
						lastUnmatchedCall = null;
						callsSinceLastUnmatchedCall = 0;
						returnsSinceLastUnmatchedCall = 0;
						afterLastUnmatchedCall = new ArrayList<>();
					} else {
						returnsSinceLastUnmatchedCall++;
						afterLastUnmatchedCall.add(currentAction);
					}
					assert callsSinceLastUnmatchedCall >= returnsSinceLastUnmatchedCall;
				} else if (currentAction instanceof ICallAction) {
					callsSinceLastUnmatchedCall++;
					afterLastUnmatchedCall.add(currentAction);
				} else {
					afterLastUnmatchedCall.add(currentAction);
				}
			}
		}

		final UnmodifiableTransFormula tfForCodeBlocks;
		if (lastUnmatchedCall == null) {
			assert afterLastUnmatchedCall.isEmpty();
			// no pending call in codeBlocks
			tfForCodeBlocks = TransFormulaUtils.sequentialComposition(csToolkit.getManagedScript(), simplify, extPqe,
					tranformToCNF, xnfConversionTechnique, simplificationTechnique, beforeFirstPendingCall, logger,
					storage);
		} else {
			// there is a pending call in codeBlocks
			assert ret == null : "no pending call between call and return possible!";
			final List<ACTION> actionsBetween = afterLastUnmatchedCall;
			tfForCodeBlocks = getInterproceduralTransFormula(csToolkit, simplify, extPqe, tranformToCNF,
					withBranchEncoders, actions.get(0).getPrecedingProcedure(),
					beforeFirstPendingCall.toArray(new UnmodifiableTransFormula[beforeFirstPendingCall.size()]),
					lastUnmatchedCall, null, actionsBetween, xnfConversionTechnique, simplificationTechnique, logger,
					storage);
		}

		UnmodifiableTransFormula result;
		if (call == null) {
			assert ret == null;
			assert beforeCall == null;
			result = tfForCodeBlocks;
		} else {
			final String calledProc = call.getSucceedingProcedure();
			if (ret == null) {
				final UnmodifiableTransFormula oldVarsAssignment = csToolkit.getOldVarsAssignmentCache()
						.getOldVarsAssignment(calledProc);
				final UnmodifiableTransFormula globalVarsAssignment = csToolkit.getOldVarsAssignmentCache()
						.getGlobalVarsAssignment(calledProc);
				final String nameEndProcedure;
				if (lastUnmatchedCall == null) {
					nameEndProcedure = calledProc;
				} else {
					nameEndProcedure = lastUnmatchedCall.getPrecedingProcedure();
				}
				final Set<IProgramNonOldVar> modifiableGlobalsOfEndProcedure = csToolkit.getModifiableGlobalsTable()
						.getModifiedBoogieVars(nameEndProcedure);
				result = TransFormulaUtils.sequentialCompositionWithPendingCall(csToolkit.getManagedScript(), simplify,
						extPqe, tranformToCNF, Arrays.asList(beforeCall), call.getLocalVarsAssignment(),
						oldVarsAssignment, globalVarsAssignment, tfForCodeBlocks, modifiableGlobalsOfEndProcedure,
						xnfConversionTechnique, simplificationTechnique, csToolkit.getSymbolTable(), nameStartProcedure,
						call.getPrecedingProcedure(), calledProc, nameEndProcedure,
						csToolkit.getModifiableGlobalsTable(), logger, storage);
			} else {
				assert beforeCall == null;
				final UnmodifiableTransFormula oldVarsAssignment = csToolkit.getOldVarsAssignmentCache()
						.getOldVarsAssignment(calledProc);
				final UnmodifiableTransFormula globalVarsAssignment = csToolkit.getOldVarsAssignmentCache()
						.getGlobalVarsAssignment(calledProc);
				result = TransFormulaUtils.sequentialCompositionWithCallAndReturn(csToolkit.getManagedScript(),
						simplify, extPqe, tranformToCNF, call.getLocalVarsAssignment(), oldVarsAssignment,
						globalVarsAssignment, tfForCodeBlocks, ret.getAssignmentOfReturn(), xnfConversionTechnique,
						simplificationTechnique, csToolkit.getSymbolTable(),
						csToolkit.getModifiableGlobalsTable().getModifiedBoogieVars(calledProc), logger, storage);
			}

		}
		return result;
	}

	@Override
	public String toString() {
		return getPrettyPrintedStatements();
	}

	@Override
	protected int getNumberOfOpenCalls() {
		return mCallsWithoutReturns;
	}
}
