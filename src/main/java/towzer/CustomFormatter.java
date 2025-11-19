package towzer;

import java.text.SimpleDateFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.Date;

/**
 *
 * @author ChatGPT
 *
 */
public class CustomFormatter extends Formatter {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public String format(LogRecord record) {
		// Format timestamp
		String timestamp = dateFormat.format(new Date(record.getMillis()));
		
		// Get the source class and line number using the stack trace
		String sourceClass = record.getSourceClassName();
		int lineNumber = -1;

		// Attempt to find the line number via stack trace
		for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			if (ste.getClassName().equals(sourceClass)) {
				lineNumber = ste.getLineNumber();
				break;
			}
		}

		// Default if we couldn't find line number
		String classAndLine = (lineNumber != -1)
				? sourceClass + " " + lineNumber
				: sourceClass;

		// Format the output
		return String.format("[%s %s %s] %s%n", timestamp, record.getLevel(), classAndLine, record.getMessage());
	}
}

