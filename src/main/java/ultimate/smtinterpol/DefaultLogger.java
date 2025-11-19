/*
 * Copyright (C) 2014 University of Freiburg
 *
 * This file is part of SMTInterpol.
 *
 * SMTInterpol is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SMTInterpol is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with SMTInterpol.  If not, see <http://www.gnu.org/licenses/>.
 */
package ultimate.smtinterpol;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Formatter;

/**
 * @author Ultimate
 * @author TOTO (Towzer)
 */
public class DefaultLogger implements LogProxy {

	// Multithreading support
	private static final Object LOCK = new Object();

	// Loglevel strings
	private static final String[] LEVELS = {
			"SEVERE",
			"WARN",
			"INFO",
			"DEBUG", // Java Logger FINE
			"TRACE" // Java Logger FINER
	};

	private PrintWriter mWriter = new PrintWriter(System.err);
	private Formatter mFormat = new Formatter(mWriter);
	private String mDest = "stderr";

	private int mLevel = Config.DEFAULT_LOG_LEVEL;

	@Override
	public void setLoglevel(int level) {
		mLevel = level;
	}

	@Override
	public int getLoglevel() {
		return mLevel;
	}

	private final boolean isEnabled(int lvl) {
		return lvl <= mLevel;
	}

	private final void log(int lvl, Object msg) {
		synchronized (LOCK) {
			mWriter.print(LEVELS[lvl - 1]);
			mWriter.print(" - ");
			mWriter.println(msg);
			mWriter.flush();
		}
	}

	private final void log(int lvl, String msg, Object[] params) {
		if (params.length == 0) {
			log(lvl, msg);
		}
		synchronized (LOCK) {
			mWriter.print(LEVELS[lvl - 1]);
			mWriter.print(" - ");
			mFormat.format(msg, params);
			mWriter.println();
			mWriter.flush();
		}
	}

	@Override
	public boolean isSevereEnabled() {
		return isEnabled(LOGLEVEL_SEVERE);
	}

	@Override
	public void severe(String msg, Object... params) {
		log(LOGLEVEL_SEVERE, msg, params);
	}

	@Override
	public void severe(Object msg) {
		log(LOGLEVEL_SEVERE, msg);
	}

	@Override
	public void outOfMemory(String msg) {
		log(LOGLEVEL_SEVERE, msg);
	}

	@Override
	public boolean isWarningEnabled() {
		return isEnabled(LOGLEVEL_WARNING);
	}

	@Override
	public void warning(String msg, Object... params) {
		log(LOGLEVEL_WARNING, msg, params);
	}

	@Override
	public void warning(Object msg) {
		log(LOGLEVEL_WARNING, msg);
	}

	@Override
	public boolean isInfoEnabled() {
		return isEnabled(LOGLEVEL_INFO);
	}

	@Override
	public void info(String msg, Object... params) {
		log(LOGLEVEL_INFO, msg, params);
	}

	@Override
	public void info(Object msg) {
		log(LOGLEVEL_INFO, msg);
	}

	@Override
	public boolean isDebugEnabled() {
		return isEnabled(LOGLEVEL_FINE);
	}

	@Override
	public void debug(String msg, Object... params) {
		log(LOGLEVEL_FINE, msg, params);
	}

	@Override
	public void debug(Object msg) {
		log(LOGLEVEL_FINE, msg);
	}

	@Override
	public boolean isTraceEnabled() {
		return isEnabled(LOGLEVEL_FINER);
	}

	@Override
	public void trace(String msg, Object... params) {
		if (isTraceEnabled()) {
			log(LOGLEVEL_FINER, msg, params);
		}
	}

	@Override
	public void trace(Object msg) {
		if (isTraceEnabled()) {
			log(LOGLEVEL_FINER, msg);
		}
	}

	@Override
	public boolean canChangeDestination() {
		return true;
	}

	@Override
	public void changeDestination(String newDest) throws IOException {
		mWriter = ChannelUtil.createChannel(newDest);
		mFormat = new Formatter(mWriter);
		mDest = newDest;
	}

	@Override
	public String getDestination() {
		return mDest;
	}

}
