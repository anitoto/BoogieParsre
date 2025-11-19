package towzer.preferences;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.fasterxml.jackson.databind.ObjectMapper;

import ultimate.blockencoding.BlockEncodingPreferences.MinimizeStates;
import ultimate.rcfgbuilder.RcfgPreference;
import ultimate.rcfgbuilder.RcfgPreference.CodeBlockSize;
import ultimate.util.boogie.Statements2TransFormula;
import ultimate.util.smt.SolverBuilder.SolverMode;

/**
 */
public class AppConfig {
	public static final String LABEL_LOG_LEVEL = "Log level";
	public static final ConfigParam<Level> log_level = new ConfigParam<>(Level.INFO);

	/** ---------- Preferences Boogie Preprocessor ---------- */

	/** Show backtranslation warnings */
	public static final String LABEL_EMIT_BACKTRANSLATION_WARNINGS = "Show backtranslation warnings";
	public static final ConfigParam<Boolean> emit_backtranslation_warnings = new ConfigParam<>(false);

	/** Simplify expressions */
	public static final String LABEL_USE_SIMPLIFIER = "Simplify expressions";
	public static final ConfigParam<Boolean> use_simplifier = new ConfigParam<>(false);

	/** ---------- Preferences Output Builder --------------- */
	public static final String LABEL_INTERPROCEDUTAL = "Interprocedural analysis (Nested Interpolants)";
	public static final ConfigParam<Boolean> interprocedural = new ConfigParam<>(true);

	/** ---------- Preferences RCFGBuilder --------------- */

	/**
	 * Add additional assume for each assert
	 * While checking some specification, assume that all other specifications hold.
	 * This is only sound in a setting where the verification process stops after
	 * the first violated specification was found.
	 */
	public static final String LABEL_ASSUME_FOR_ASSERT = "Add additional assume for each assert";
	public static final boolean DEF_ASSUME_FOR_ASSERT = true;
	public static final ConfigParam<Boolean> assume_for_assert = new ConfigParam<>(DEF_ASSUME_FOR_ASSERT);

	/** Translate Boogie integers to SMT bitvectors */
	public static final String LABEL_BITVECTOR_WORKAROUND = "Translate Boogie integers to SMT bitvectors";
	public static final ConfigParam<Boolean> bitvector_workaround = new ConfigParam<>(false);

	/** Size of a code block */
	public static final String LABEL_CODE_BLOCK_SIZE = "Size of a code block";
	public static final CodeBlockSize DEF_CODE_BLOCK_SIZE = CodeBlockSize.SingleStatement;
	public static final ConfigParam<CodeBlockSize> code_block_size = new ConfigParam<>(DEF_CODE_BLOCK_SIZE);

	/** Dump SMT script to file */
	public static final String LABEL_DUMP_TO_FILE = "Dump SMT script to file";
	public static final ConfigParam<Boolean> dump_to_file = new ConfigParam<>(false);

	/** Dump SMT script to file to the following directory */
	public static final String LABEL_DUMP_PATH = "To the following directory";
	public static final ConfigParam<String> dump_path = new ConfigParam<>("");

	/** Dump unsat core track benchmark to file */
	public static final String LABEL_DUMP_UNSAT_CORE_BENCHMARK = "Dump unsat core track benchmark to file";
	public static final ConfigParam<Boolean> dump_unsat_core_benchmark = new ConfigParam<>(false);

	/** Dump main track benchmark to file */
	public static final String LABEL_DUMP_MAIN_TRACK_BENCHMARK = "Dump main track benchmark to file";
	public static final ConfigParam<Boolean> dump_main_track_benchmark = new ConfigParam<>(false);

	/** Command for external solver */
	public static final String LABEL_EXT_SOLVER_COMMAND = "Command for external solver";
	public static final ConfigParam<String> ext_solver_command = new ConfigParam<>(RcfgPreference.Z3_DEFAULT);

	/** Logic for external solver */
	public static final String LABEL_EXT_SOLVER_LOGIC = "Logic for external solver";
	public static final ConfigParam<String> ext_solver_logic = new ConfigParam<>("ALL");

	/** Fake non-incremental script */
	public static final String LABEL_FAKE_NON_INCREMENTAL_SCRIPT = "Fake non-incremental script";
	public static final ConfigParam<Boolean> fake_non_incremental_script = new ConfigParam<>(false);

	/** Convert code blocks to CNF */
	public static final String LABEL_CNF = "Convert code blocks to CNF";
	public static final ConfigParam<Boolean> cnf = new ConfigParam<>(false);

	/** Remove goto edges from RCFG */
	public static final String LABEL_REMOVE_GOTO_EDGES = "Remove goto edges from RCFG";
	public static final ConfigParam<Boolean> remove_goto_edges = new ConfigParam<>(false);

	/**
	 * Skolemize terms
	 * 
	 * @see Statements2TransFormula#mSimplePartialSkolemization
	 */
	public static final String LABEL_SIMPLE_PARTIAL_SKOLEMIZATION = "Skolemize terms";
	public static final ConfigParam<Boolean> simple_partial_skolemization = new ConfigParam<>(true);

	/** Simplify code blocks */
	public static final String LABEL_SIMPLIFY = "Simplify code blocks";
	public static final ConfigParam<Boolean> simplify = new ConfigParam<>(false);

	/** SMT solver */
	public static final String LABEL_SOLVER = "SMT solver mode";
	public static final SolverMode DEF_SOLVER = SolverMode.Internal_SMTInterpol;
	public static final ConfigParam<SolverMode> solver = new ConfigParam<>(DEF_SOLVER);

	/** ---------- Preferences BlockEncoding --------------- */

	// --- Pre-processing
	
	/** Use SBE */
	public static final String LABEL_PRE_SBE = "Use SBE";
	public static final ConfigParam<Boolean> pre_sbe = new ConfigParam<>(false);

	/** Rewrite not-equals */
	public static final String LABEL_PRE_REWRITENOTEQUALS = "Rewrite not-equals";
	public static final ConfigParam<Boolean> pre_rewritenotequals = new ConfigParam<>(false);

	// --- Iterative encodings

	/** Create interprocedural compositions */
	public static final String LABEL_FXP_INTERPROCEDURAL_COMPOSITION = "Create interprocedural compositions";
	public static final ConfigParam<Boolean> fxp_interprocedural_composition = new ConfigParam<>(true);

	/** Maximize final states */
	public static final String LABEL_FXP_MAXIMIZE_FINAL_STATES = "Maximize final states";
	public static final ConfigParam<Boolean> fxp_maximize_final_states = new ConfigParam<>(false);

	/** Minimize states using LBE with the strategy */
	public static final String LABEL_FXP_MINIMIZE_STATES = "Minimize states using LBE with the strategy";
	public static final ConfigParam<MinimizeStates> fxp_minimize_states = new ConfigParam<>(MinimizeStates.NONE);

	/** Minimize states even if more edges are added than removed */
	public static final String LABEL_FXP_MINIMIZE_STATES_IGNORE_BLOWUP = "Minimize states even if more edges are added than removed";
	public static final ConfigParam<Boolean> fxp_minimize_states_ignore_blowup = new ConfigParam<>(true);

	/** Remove infeasible edges */
	public static final String LABEL_FXP_REMOVE_INFEASIBLE_EDGES = "Remove infeasible edges";
	public static final ConfigParam<Boolean> fxp_remove_infeasible_edges = new ConfigParam<>(true);

	/** Remove sink states */
	public static final String LABEL_FXP_REMOVE_SINK_STATES = "Remove infeasible edges";
	public static final ConfigParam<Boolean> fxp_remove_sink_states = new ConfigParam<>(true);

	/** Apply optimizations until nothing changes */
	public static final String LABEL_FXP_UNTIL_FIXPOINT = "Apply optimizations until nothing changes";
	public static final ConfigParam<Boolean> fxp_until_fixpoint = new ConfigParam<>(true);

	/** Iterate optimizations for n times (<=0 means until nothing changes) */
	public static final String LABEL_FXP_MAX_ITERATIONS = "Iterate optimizations for n times";
	public static final ConfigParam<Integer> fxp_max_iterations = new ConfigParam<>(0);

	// --- Post processing
	
	/** Simplify transitions */
	public static final String LABEL_POST_SIMPLIFY_TRANSITIONS = "Simplify transitions";
	public static final ConfigParam<Boolean> post_simplify_transitions = new ConfigParam<>(false);

	/** Create parallel compositions if possible */
	public static final String LABEL_POST_USE_PARALLEL_COMPOSITION = "Create parallel compositions if possible";
	public static final ConfigParam<Boolean> post_use_parallel_composition = new ConfigParam<>(true);
	
	/** ---------- Save user preferences to JSON ------------ */

	/** Save user values to JSON */
	public static void saveToJson(String path) throws IOException {
		Map<String, Object> userValues = new HashMap<>();
		for (Field field : AppConfig.class.getDeclaredFields()) {
			try {
				Object obj = field.get(null); // static field: instance is null
				if (obj instanceof ConfigParam) {
					ConfigParam<?> cp = (ConfigParam<?>) obj;
					if (cp.isUserSet()) {
						userValues.put(field.getName(), cp.get().toString());
					}
				}
			} catch (IllegalAccessException e) {
				System.err.println("Cannot access field: " + field.getName());
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(path).toFile(), userValues);
	}

	/** Load user values from JSON */
	public static void loadFromJson(String path) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, String> values = mapper.readValue(Paths.get(path).toFile(), Map.class);
		for (Map.Entry<String, String> entry : values.entrySet()) {
			try {
				Field field = AppConfig.class.getDeclaredField(entry.getKey());
				Object obj = field.get(null); // static field: instance is null
				if (obj instanceof ConfigParam) {
					ConfigParam<?> cp = (ConfigParam<?>) obj;
					Object defaultValue = cp.getDefault();
					if (defaultValue != null) {
						Object typedValue = parseValue(defaultValue.getClass(), entry.getValue());
						@SuppressWarnings("unchecked")
						ConfigParam<Object> casted = (ConfigParam<Object>) cp;
						casted.set(typedValue);
					}
				}
			} catch (Exception e) {
				System.err.println("Failed to load config param: " + entry.getKey());
				e.printStackTrace();
			}
		}
	}

	private static Object parseValue(Class<?> clazz, String value) {
		if (clazz == Boolean.class)
			return Boolean.parseBoolean(value);
		if (clazz == String.class)
			return value;
		if (clazz == Level.class)
			return Level.parse(value);
		if (clazz.isEnum()) {
			@SuppressWarnings("unchecked")
			Class<? extends Enum> enumClass = (Class<? extends Enum>) clazz;
			return Enum.valueOf(enumClass, value);
		}
		throw new IllegalArgumentException("Unsupported type: " + clazz);
	}

	public static <T> void setIfNotNull(ConfigParam<T> variable, T value) {
		if (value != null) {
			variable.set(value);
		}
	}

	public static void interactiveSetup() {
		System.out.println("SETUP PREFERENCES");
		System.out.println("Leave empty to use default value (press Enter without input).");

		setIfNotNull(log_level, AppConfigUtils.chooseLogLevel());

		System.out.println("--- Boogie Preprocessor");
		setIfNotNull(emit_backtranslation_warnings, AppConfigUtils.chooseBoolean(LABEL_EMIT_BACKTRANSLATION_WARNINGS));
		setIfNotNull(use_simplifier, AppConfigUtils.chooseBoolean(LABEL_USE_SIMPLIFIER));

		System.out.println("--- Output Builder");
		setIfNotNull(interprocedural, AppConfigUtils.chooseBoolean(LABEL_INTERPROCEDUTAL));

		System.out.println("--- RCFGBuilder");
		setIfNotNull(assume_for_assert, AppConfigUtils.chooseBoolean(LABEL_ASSUME_FOR_ASSERT));
		setIfNotNull(bitvector_workaround, AppConfigUtils.chooseBoolean(LABEL_BITVECTOR_WORKAROUND));
		setIfNotNull(code_block_size, AppConfigUtils.chooseEnum(LABEL_CODE_BLOCK_SIZE, CodeBlockSize.class));
		setIfNotNull(dump_to_file, AppConfigUtils.chooseBoolean(LABEL_DUMP_TO_FILE));
		setIfNotNull(dump_path, AppConfigUtils.chooseValidPath(LABEL_DUMP_PATH));
		setIfNotNull(dump_unsat_core_benchmark, AppConfigUtils.chooseBoolean(LABEL_DUMP_UNSAT_CORE_BENCHMARK));
		setIfNotNull(dump_main_track_benchmark, AppConfigUtils.chooseBoolean(LABEL_DUMP_MAIN_TRACK_BENCHMARK));
		setIfNotNull(ext_solver_command, AppConfigUtils.chooseSolverCommand());
		setIfNotNull(ext_solver_logic, AppConfigUtils.chooseString(LABEL_EXT_SOLVER_LOGIC, true));
		setIfNotNull(solver, AppConfigUtils.chooseEnum(LABEL_SOLVER, SolverMode.class));
		setIfNotNull(fake_non_incremental_script, AppConfigUtils.chooseBoolean(LABEL_FAKE_NON_INCREMENTAL_SCRIPT));
		setIfNotNull(cnf, AppConfigUtils.chooseBoolean(LABEL_CNF));
		setIfNotNull(remove_goto_edges, AppConfigUtils.chooseBoolean(LABEL_REMOVE_GOTO_EDGES));
		setIfNotNull(simple_partial_skolemization, AppConfigUtils.chooseBoolean(LABEL_SIMPLE_PARTIAL_SKOLEMIZATION));
		setIfNotNull(simplify, AppConfigUtils.chooseBoolean(LABEL_SIMPLIFY));

		System.out.println("--- BlockEncoding");
		setIfNotNull(pre_sbe, AppConfigUtils.chooseBoolean(LABEL_PRE_SBE));
		setIfNotNull(pre_rewritenotequals, AppConfigUtils.chooseBoolean(LABEL_PRE_REWRITENOTEQUALS));
		setIfNotNull(fxp_interprocedural_composition, AppConfigUtils.chooseBoolean(LABEL_FXP_INTERPROCEDURAL_COMPOSITION));
		setIfNotNull(fxp_maximize_final_states, AppConfigUtils.chooseBoolean(LABEL_FXP_MAXIMIZE_FINAL_STATES));
		setIfNotNull(fxp_minimize_states, AppConfigUtils.chooseEnum(LABEL_FXP_MINIMIZE_STATES, MinimizeStates.class));
		setIfNotNull(fxp_minimize_states_ignore_blowup, AppConfigUtils.chooseBoolean(LABEL_FXP_MINIMIZE_STATES_IGNORE_BLOWUP));
		setIfNotNull(fxp_remove_infeasible_edges, AppConfigUtils.chooseBoolean(LABEL_FXP_REMOVE_INFEASIBLE_EDGES));
		setIfNotNull(fxp_remove_sink_states, AppConfigUtils.chooseBoolean(LABEL_FXP_REMOVE_SINK_STATES));
		setIfNotNull(fxp_until_fixpoint, AppConfigUtils.chooseBoolean(LABEL_FXP_UNTIL_FIXPOINT));
		// TODO: fxp_max_iterations si pertinent
		setIfNotNull(post_simplify_transitions, AppConfigUtils.chooseBoolean(LABEL_POST_SIMPLIFY_TRANSITIONS));
		setIfNotNull(post_use_parallel_composition, AppConfigUtils.chooseBoolean(LABEL_POST_USE_PARALLEL_COMPOSITION));
	}
}
