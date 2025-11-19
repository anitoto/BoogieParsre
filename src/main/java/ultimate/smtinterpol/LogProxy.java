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

/**
 * A proxy for different logging frameworks. This proxy provides the basic
 * features needed by SMTInterpol. It can be instantiated to different logging
 * frameworks making SMTInterpol independent of such frameworks.
 * 
 * @author Juergen Christ
 */
public interface LogProxy {

	/*
	 * Log levels such that a message is logged if the enabled level is greater
	 * or equal than the level of the message.
	 */
	// Java Logger Level Integer Values
	public static int LOGLEVEL_OFF = Integer.MAX_VALUE;
	public static int LOGLEVEL_SEVERE = 1000;
	public static int LOGLEVEL_WARNING = 900;
	public static int LOGLEVEL_INFO = 800;
	public static int LOGLEVEL_FINE = 500;
	public static int LOGLEVEL_FINER = 400;
	public static int LOGLEVEL_ALL = Integer.MIN_VALUE;

	// Getter and setter for the log level of this proxy.
	void setLoglevel(int level);

	int getLoglevel();

	/*
	 * Fatal/Error messages.
	 * Note that in a system based on LogRecords, the outOfMemory function should
	 * _not_ log anything as it will produce the next OutOfMemoryError.
	 */
	boolean isSevereEnabled();
	void severe(String msg, Object... params);
	void severe(Object msg);
	void outOfMemory(String msg);

	// Warning messages.
	boolean isWarningEnabled();
	void warning(String msg, Object... params);
	void warning(Object msg);

	// Info messages.
	boolean isInfoEnabled();
	void info(String msg, Object... params);
	void info(Object msg);

	// Debug messages.
	boolean isDebugEnabled();
	void debug(String msg, Object... params);
	void debug(Object msg);

	// Trace messages.
	boolean isTraceEnabled();
	void trace(String msg, Object... params);
	void trace(Object msg);

	// Output destination management
	/**
	 * Check if the logger can change its destination.
	 * 
	 * @return <code>true</code> if the destination of the log messages can be
	 *         changed by this logger.
	 */
	boolean canChangeDestination();

	/**
	 * Change to a new destination. The destination can be <code>stdout</code>
	 * for the standard output channel of the process, <code>stderr</code> for
	 * the standard error channel, or a name of a file. The option map ensures
	 * that this function is called only if {@link #canChangeDestination()}
	 * returned <code>true</code>.
	 * 
	 * @param newDest The new destination.
	 * @throws IOException If the new destination cannot be created.
	 */
	void changeDestination(String newDest) throws IOException;

	/**
	 * Gets a string representation of the current destination. The output has
	 * to be valid input for {@link #changeDestination(String)}.
	 * 
	 * @return The current destination.
	 */
	String getDestination();
}
