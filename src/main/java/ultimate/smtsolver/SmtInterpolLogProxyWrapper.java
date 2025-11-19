/*
 * Copyright (C) 2016 Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * Copyright (C) 2016 University of Freiburg
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
package ultimate.smtsolver;

import java.io.IOException;
import java.util.Formatter;
import java.util.logging.Logger;

import ultimate.smtinterpol.LogProxy;

/**
 * This wrapper allows you to use an Ultimate {@link ILogger} instead of
 * SMTInterpols {@link LogProxy}.
 *
 * @author Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * @author TOTO (Towzer)
 */
public class SmtInterpolLogProxyWrapper implements LogProxy {

	private final Logger logger;

	public SmtInterpolLogProxyWrapper(final Logger logger) {
		this.logger = logger;
	}

	@Override
	public void setLoglevel(final int level) {
		// we ignore changes to our log level from the outside
	}

	@Override
	public int getLoglevel() {
		return logger.getLevel().intValue();
	}

	@Override
	public boolean isSevereEnabled() {
		return this.getLoglevel() <= LOGLEVEL_SEVERE;
	}

	@Override
	public boolean isWarningEnabled() {
		return this.getLoglevel() <= LOGLEVEL_WARNING;
	}

	@Override
	public boolean isInfoEnabled() {
		return this.getLoglevel() <= LOGLEVEL_INFO;
	}

	@Override
	public boolean isDebugEnabled() {
		return this.getLoglevel() <= LOGLEVEL_FINE;
	}

	@Override
	public boolean isTraceEnabled() {
		return this.getLoglevel() <= LOGLEVEL_FINER;
	}

	@Override
	public void outOfMemory(final String msg) {
		severe(msg);
	}

	@Override
	public void severe(final Object msg) {
		log(LOGLEVEL_SEVERE, String.valueOf(msg));
	}

	@Override
	public void warning(final Object msg) {
		log(LOGLEVEL_WARNING, String.valueOf(msg));
	}

	@Override
	public void info(final Object msg) {
		log(LOGLEVEL_INFO, String.valueOf(msg));
	}

	@Override
	public void debug(final Object msg) {
		log(LOGLEVEL_FINE, String.valueOf(msg));
	}

	@Override
	public void trace(final Object msg) {
		log(LOGLEVEL_FINER, String.valueOf(msg));
	}

	@Override
	public void severe(final String msg, final Object... params) {
		log(LOGLEVEL_SEVERE, msg, params);
	}

	@Override
	public void warning(final String msg, final Object... params) {
		log(LOGLEVEL_WARNING, msg, params);
	}

	@Override
	public void info(final String msg, final Object... params) {
		log(LOGLEVEL_INFO, msg, params);
	}

	@Override
	public void debug(final String msg, final Object... params) {
		log(LOGLEVEL_FINE, msg, params);
	}

	@Override
	public void trace(final String msg, final Object... params) {
		log(LOGLEVEL_FINER, msg, params);
	}

	@Override
	public boolean canChangeDestination() {
		return false;
	}

	@Override
	public void changeDestination(final String newDest) throws IOException {
		// we can never change the destination
	}

	@Override
	public String getDestination() {
		// we just say we log to stdout because we support many more destinations and
		// they are not controlled by the
		// logger itself
		return "stdout";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (logger == null ? 0 : logger.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SmtInterpolLogProxyWrapper other = (SmtInterpolLogProxyWrapper) obj;
		if (logger == null) {
			if (other.logger != null) {
				return false;
			}
		} else if (!logger.equals(other.logger)) {
			return false;
		}
		return true;
	}

	private void log(final int lvl, final String msg) {
		switch (lvl) {
			case LOGLEVEL_OFF:
				return;
			case LOGLEVEL_FINER:
				logger.finer(msg);
				return;
			case LOGLEVEL_FINE:
				logger.fine(msg);
				return;
			case LOGLEVEL_INFO:
				logger.info(msg);
				return;
			case LOGLEVEL_WARNING:
				logger.warning(msg);
				return;
			case LOGLEVEL_SEVERE:
				logger.severe(msg);
				return;
			default:
				logger.severe("Unsupported log level: " + msg);
		}
	}

	private void log(final int lvl, final String msg, final Object[] params) {
		if (params.length == 0) {
			log(lvl, msg);
		} else {
			log(lvl, convert(msg, params));
		}
	}

	private static String convert(final String msg, final Object[] params) {
		// I do not think that this is correct, but I do it as I see it in SMTInterpols
		// DefaultLogger
		final StringBuilder sb = new StringBuilder();
		final Formatter formatter = new Formatter(sb);
		formatter.format(msg, params);
		formatter.close();
		return sb.toString();
	}
}
