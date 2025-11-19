package ultimate.rcfgbuilder.util;

import ultimate.logic.Term;
import ultimate.translation.AtomicTraceElement;
import ultimate.translation.IBacktranslationValueProvider;
import ultimate.translation.IProgramExecution;
import ultimate.util.cfg.structure.IIcfgTransition;
import ultimate.util.cfg.structure.IcfgLocation;

public class IcfgAngelicProgramExecution implements IProgramExecution<IIcfgTransition<IcfgLocation>, Term> {

	private final boolean mAngelicStatus;
	private final IProgramExecution<IIcfgTransition<IcfgLocation>, Term> mProgramExecution;

	public IcfgAngelicProgramExecution(final IProgramExecution<IIcfgTransition<IcfgLocation>, Term> pe,
			final boolean angelicStatus) {
		mProgramExecution = pe;
		mAngelicStatus = angelicStatus;
	}

	public boolean getAngelicStatus() {
		return mAngelicStatus;
	}

	@Override
	public int getLength() {
		return mProgramExecution.getLength();
	}

	@Override
	public AtomicTraceElement<IIcfgTransition<IcfgLocation>> getTraceElement(final int index) {
		return mProgramExecution.getTraceElement(index);
	}

	@Override
	public ProgramState<Term> getProgramState(final int index) {
		return mProgramExecution.getProgramState(index);
	}

	@Override
	public ProgramState<Term> getInitialProgramState() {
		return mProgramExecution.getInitialProgramState();
	}

	@Override
	public Class<Term> getExpressionClass() {
		return mProgramExecution.getExpressionClass();
	}

	@Override
	public Class<? extends IIcfgTransition<IcfgLocation>> getTraceElementClass() {
		return mProgramExecution.getTraceElementClass();
	}

	@Override
	public IBacktranslationValueProvider<IIcfgTransition<IcfgLocation>, Term> getBacktranslationValueProvider() {
		return mProgramExecution.getBacktranslationValueProvider();
	}

	@Override
	public boolean isConcurrent() {
		return mProgramExecution.isConcurrent();
	}

}
