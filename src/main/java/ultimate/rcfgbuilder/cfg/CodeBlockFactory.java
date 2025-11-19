/*
 * Copyright (C) 2015 Matthias Heizmann (heizmann@informatik.uni-freiburg.de)
 * Copyright (C) 2018 Lars Nitzke (lars.nitzke@outlook.com)
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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ultimate.Storage;
import ultimate.ast.CallStatement;
import ultimate.ast.ForkStatement;
import ultimate.ast.JoinStatement;
import ultimate.ast.Statement;
import ultimate.models.IStorable;
import ultimate.rcfgbuilder.cfg.StatementSequence.Origin;
import ultimate.util.cfg.CfgSmtToolkit;
import ultimate.util.cfg.IIcfgSymbolTable;
import ultimate.util.datastructures.SerialProvider;
import ultimate.util.smt.SmtUtils.SimplificationTechnique;
import ultimate.util.smt.SmtUtils.XnfConversionTechnique;
import ultimate.util.smt.managedscript.ManagedScript;

/**
 * Factory for the construction of CodeBlocks. Every CodeBlock has to be
 * constructed via this factory, because every
 * CodeBlock need a unique serial number. Every control flow graph has to
 * provide one CodeBlockFactory
 *
 * @author Matthias Heizmann
 * @author TOTO (Towzer)
 *
 */
public class CodeBlockFactory implements IStorable {

	// Global Java logger
	private final Logger logger;

	private final Storage storage;

	private final ManagedScript mMgdScript;
	private final CfgSmtToolkit mMgvManager;
	private final SerialProvider mSerialProvider;

	public CodeBlockFactory(final ManagedScript mgdScript, final CfgSmtToolkit mgvManager,
			final IIcfgSymbolTable symbolTable, final SerialProvider serialProvider, final Logger logger,
			final Storage storage) {
		super();
		this.logger = logger;
		this.storage = storage;
		mSerialProvider = serialProvider;
		mMgdScript = mgdScript;
		mMgvManager = mgvManager;
	}

	public Call constructCall(final BoogieIcfgLocation source, final BoogieIcfgLocation target,
			final CallStatement call) {
		return new Call(makeFreshSerial(), source, target, call, logger);
	}

	public ForkThreadCurrent constructForkCurrentThread(final BoogieIcfgLocation source,
			final BoogieIcfgLocation target, final ForkStatement fork, final boolean forkedProcedureHasImplementation) {
		return new ForkThreadCurrent(makeFreshSerial(), source, target, fork, forkedProcedureHasImplementation, logger);
	}

	public JoinThreadCurrent constructJoinCurrentThread(final BoogieIcfgLocation source,
			final BoogieIcfgLocation target, final JoinStatement join) {
		return new JoinThreadCurrent(makeFreshSerial(), source, target, join, logger);
	}

	public ForkThreadOther constructForkOtherThread(final BoogieIcfgLocation source, final BoogieIcfgLocation target,
			final ForkStatement fork, final ForkThreadCurrent forkCurrentThread) {
		return new ForkThreadOther(makeFreshSerial(), source, target, fork, forkCurrentThread, logger);
	}

	public JoinThreadOther constructJoinOtherThread(final BoogieIcfgLocation source, final BoogieIcfgLocation target,
			final JoinStatement join, final JoinThreadCurrent joinCurrentThread) {
		return new JoinThreadOther(makeFreshSerial(), source, target, join, joinCurrentThread, logger);
	}

	public GotoEdge constructGotoEdge(final BoogieIcfgLocation source, final BoogieIcfgLocation target) {
		return new GotoEdge(makeFreshSerial(), source, target, logger);
	}

	public ParallelComposition constructParallelComposition(final BoogieIcfgLocation source,
			final BoogieIcfgLocation target, final List<CodeBlock> codeBlocks,
			final XnfConversionTechnique xnfConversionTechnique,
			final SimplificationTechnique simplificationTechnique) {
		return new ParallelComposition(makeFreshSerial(), source, target, mMgdScript, codeBlocks,
				xnfConversionTechnique, logger, storage);
	}

	public Return constructReturn(final BoogieIcfgLocation source, final BoogieIcfgLocation target,
			final Call correspondingCall) {
		return new Return(makeFreshSerial(), source, target, correspondingCall, logger);
	}

	public SequentialComposition constructSequentialComposition(final BoogieIcfgLocation source,
			final BoogieIcfgLocation target, final boolean simplify, final boolean extPqe,
			final List<CodeBlock> codeBlocks, final XnfConversionTechnique xnfConversionTechnique,
			final SimplificationTechnique simplificationTechnique) {
		return new SequentialComposition(makeFreshSerial(), source, target, mMgvManager, simplify, extPqe, codeBlocks,
				xnfConversionTechnique, simplificationTechnique, logger, storage);
	}

	public StatementSequence constructStatementSequence(final BoogieIcfgLocation source,
			final BoogieIcfgLocation target, final Statement st) {
		return new StatementSequence(makeFreshSerial(), source, target, st, logger);
	}

	public StatementSequence constructStatementSequence(final BoogieIcfgLocation source,
			final BoogieIcfgLocation target, final Statement st, final Origin origin) {
		return new StatementSequence(makeFreshSerial(), source, target, st, origin, logger);
	}

	public StatementSequence constructStatementSequence(final BoogieIcfgLocation source,
			final BoogieIcfgLocation target, final List<Statement> stmts, final Origin origin) {
		return new StatementSequence(makeFreshSerial(), source, target, stmts, origin, logger);
	}

	public Summary constructSummary(final BoogieIcfgLocation source, final BoogieIcfgLocation target,
			final CallStatement st, final boolean calledProcedureHasImplementation) {
		return new Summary(makeFreshSerial(), source, target, st, calledProcedureHasImplementation, logger);
	}

	private int makeFreshSerial() {
		return mSerialProvider.getFreshSerial();
	}

	public CodeBlock copyCodeBlock(final CodeBlock codeBlock, final BoogieIcfgLocation source,
			final BoogieIcfgLocation target) {
		if (codeBlock instanceof Call) {
			final Call copy = constructCall(source, target, ((Call) codeBlock).getCallStatement());
			copy.setTransitionFormula(codeBlock.getTransformula());
			return copy;
		} else if (codeBlock instanceof Return) {
			// FIXME: The return keeps its reference to the old call and thus to a possibly
			// old Icfg
			final Return copy = constructReturn(source, target, ((Return) codeBlock).getCorrespondingCall());
			copy.setTransitionFormula(codeBlock.getTransformula());
			return copy;
		} else if (codeBlock instanceof StatementSequence) {
			final List<Statement> statements = ((StatementSequence) codeBlock).getStatements();
			final Origin origin = ((StatementSequence) codeBlock).getOrigin();
			final StatementSequence copy = this.constructStatementSequence(source, target, statements, origin);
			copy.setTransitionFormula(codeBlock.getTransformula());
			return copy;
		} else if (codeBlock instanceof Summary) {
			final CallStatement callStatement = ((Summary) codeBlock).getCallStatement();
			final boolean calledProcedureHasImplementation = ((Summary) codeBlock).calledProcedureHasImplementation();
			final Summary copy = constructSummary(source, target, callStatement, calledProcedureHasImplementation);
			copy.setTransitionFormula(codeBlock.getTransformula());
			return copy;
		} else if (codeBlock instanceof GotoEdge) {
			return constructGotoEdge(source, target);
		} else if (codeBlock instanceof SequentialComposition) {
			final SequentialComposition comp = (SequentialComposition) codeBlock;
			List<CodeBlock> blks = comp.getCodeBlocks();
			List<CodeBlock> blkCopies = new ArrayList<>();
			for (CodeBlock blk : blks) {
				blkCopies.add(copyCodeBlock(blk, source, target));
			}
			return constructSequentialComposition(source, target, false, true, blkCopies,
					comp.getXnfConversionTechnique(), comp.getSimplificationTechnique());
		} else {
			throw new UnsupportedOperationException(
					"unsupported kind of CodeBlock: " + codeBlock.getClass().getSimpleName());
		}
	}

	@Override
	public void destroy() {
		// nothing to destroy
	}

	public static CodeBlockFactory getFactory(final Storage storage) {
		return (CodeBlockFactory) storage.getStorable(CodeBlockFactory.class.getName());
	}

	public void storeFactory() {
		storage.putStorable(CodeBlockFactory.class.getName(), this);
	}

}
