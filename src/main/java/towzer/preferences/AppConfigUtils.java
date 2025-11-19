package towzer.preferences;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;

import ultimate.rcfgbuilder.RcfgPreference;

/**
 */
public class AppConfigUtils {
	private static final Scanner scanner = new Scanner(System.in);

	public static Boolean chooseBoolean(String message) {
		System.out.printf("%s (0 = false, 1 = true): ", message);
		while (true) {
			String input = scanner.nextLine().trim();
			if (input.isEmpty()) return null;
			if (input.equals("0")) return false;
			if (input.equals("1")) return true;
			System.out.print("Invalid input. Enter 0 or 1: ");
		}
	}

	public static String chooseString(String message, boolean mustNotBeEmpty) {
		System.out.println(message);
		while (true) {
			System.out.print("Enter value: ");
			String input = scanner.nextLine().trim();
			if (input.isEmpty()) return null;
			if (!mustNotBeEmpty || !input.isEmpty()) return input;
			System.out.println("Input cannot be empty.");
		}
	}

	public static <E extends Enum<E>> E chooseEnum(String message, Class<E> enumClass) {
		E[] values = enumClass.getEnumConstants();
		System.out.println(message + ":");
		for (int i = 0; i < values.length; i++) {
			System.out.printf("  %d. %s%n", i, values[i]);
		}
		while (true) {
			System.out.print("Enter choice: ");
			String input = scanner.nextLine().trim();
			if (input.isEmpty()) return null;
			try {
				int choice = Integer.parseInt(input);
				if (choice >= 0 && choice < values.length) return values[choice];
			} catch (NumberFormatException ignored) {}
			System.out.println("Invalid input. Try again.");
		}
	}

	public static String chooseValidPath(String message) {
		System.out.println(message);
		while (true) {
			System.out.print("Enter path: ");
			String input = scanner.nextLine().trim();
			if (input.isEmpty()) return null;
			Path path = Paths.get(input).getParent();
			if (path == null || Files.exists(path)) return input;
			System.out.println("Parent folder doesn't exist. Try again.");
		}
	}

	public static Level chooseLogLevel() {
		Level[] levels = {
				Level.SEVERE,   // 0
				Level.WARNING,  // 1
				Level.INFO,     // 2
				Level.FINE,     // 3 - debug
				Level.FINER,    // 4 - trace
				Level.ALL,      // 5
				Level.OFF       // 6
		};

		String[] labels = {
				"SEVERE",
				"WARNING",
				"INFO",
				"FINE (debug)",
				"FINER (trace)",
				"ALL",
				"OFF"
		};

		System.out.println("Choose log level:");
		for (int i = 0; i < levels.length; i++) {
			System.out.printf("  %d. %s%n", i, labels[i]);
		}

		while (true) {
			System.out.print("Enter choice (0-" + (levels.length - 1) + "): ");
			String input = scanner.nextLine().trim();
			if (input.isEmpty()) return null;
			try {
				int choice = Integer.parseInt(input);
				if (choice >= 0 && choice < levels.length) return levels[choice];
			} catch (NumberFormatException ignored) {}
			System.out.println("Invalid input. Please try again.");
		}
	}

	public static String chooseSolverCommand() {
		String[] options = {
				RcfgPreference.Z3_DEFAULT,
				RcfgPreference.Z3_NO_EXTENSIONAL_ARRAYS,
				RcfgPreference.Z3_NO_MBQI,
				RcfgPreference.Z3_LOW_TIMEOUT,
				RcfgPreference.CVC4,
				RcfgPreference.Princess
		};

		System.out.println(AppConfig.LABEL_EXT_SOLVER_COMMAND + ":");
		for (int i = 0; i < options.length; i++) {
			System.out.printf("  %d. %s%n", i, options[i]);
		}

		while (true) {
			System.out.print("Enter choice: ");
			String input = scanner.nextLine().trim();
			if (input.isEmpty()) return null;
			try {
				int choice = Integer.parseInt(input);
				if (choice >= 0 && choice < options.length) {
					if (options[choice].equals("custom")) {
						System.out.print("Enter custom solver command: ");
						String custom = scanner.nextLine().trim();
						if (custom.isEmpty()) return null;
						return custom;
					}
					return options[choice];
				}
			} catch (NumberFormatException ignored) {}
			System.out.println("Invalid input. Try again.");
		}
	}
}
