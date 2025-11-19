package ultimate.util.smt;

import java.util.Comparator;
import java.util.logging.Logger;

import ultimate.util.smt.arrays.ArrayIndex;

/**
 * @author Ultimate
 * @author TOTO (Towzer)
 */
public class ComparatorArrayIndex implements Comparator<ArrayIndex> {
	// Global Java logger
	private final Logger logger;

	public ComparatorArrayIndex(final Logger logger) {
		this.logger = logger;
	}

	@Override
	public int compare(final ArrayIndex o1, final ArrayIndex o2) {
		return o2.getFreeVars(logger).size() - o1.getFreeVars(logger).size();
	}

}
