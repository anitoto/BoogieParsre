/*
 * Copyright (C) 2014-2015 Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * Copyright (C) 2015 University of Freiburg
 * 
 * This file is part of the ULTIMATE BoogiePreprocessor plug-in.
 * 
 * The ULTIMATE BoogiePreprocessor plug-in is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * The ULTIMATE BoogiePreprocessor plug-in is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ULTIMATE BoogiePreprocessor plug-in. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Additional permission under GNU GPL version 3 section 7:
 * If you modify the ULTIMATE BoogiePreprocessor plug-in, or any covered work, by linking
 * or combining it with Eclipse RCP (or a modified version of Eclipse RCP),
 * containing parts covered by the terms of the Eclipse Public License, the
 * licensors of the ULTIMATE BoogiePreprocessor plug-in grant you additional permission
 * to convey the resulting work.
 */
package ultimate.symboltable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ultimate.ast.Attribute;
import ultimate.ast.Body;
import ultimate.ast.BoogieVisitor;
import ultimate.ast.Declaration;
import ultimate.ast.DeclarationInformation.StorageClass;
import ultimate.ast.Expression;
import ultimate.ast.FunctionDeclaration;
import ultimate.ast.FunctionDeclaration;
import ultimate.ast.IdentifierExpression;
import ultimate.ast.NamedAttribute;
import ultimate.ast.Procedure;
import ultimate.ast.Procedure;
import ultimate.ast.StringLiteral;
import ultimate.ast.VarList;
import ultimate.ast.VariableDeclaration;
import ultimate.models.IBoogieType;
import ultimate.type.BoogieArrayType;
import ultimate.type.BoogiePrimitiveType;

/**
 * BoogieSymbolTable is a symbol table for all scopes of a Boogie program.
 * 
 * It is still work in progress, so there are no final comments.
 * 
 * @author dietsch@informatik.uni-freiburg.de
 * 
 */
public class BoogieSymbolTable {

	private final Map<StorageClass, Map<String, Map<String, Declaration>>> mSymbolTable;

	public BoogieSymbolTable() {
		mSymbolTable = new LinkedHashMap<>();
	}

	/**
	 * Add a procedure or function declaration to the symbol map. The symbol map
	 * will decide if this is an
	 * implementation, procedure, or function and store it accordingly.
	 * 
	 * @param symbolName
	 * @param decl
	 */
	protected void addProcedureOrFunction(final String symbolName, final Procedure decl) {
		final Map<String, Declaration> procMap = getProcedureMap(decl);
		if (procMap.containsKey(symbolName)) {
			throw new IllegalArgumentException("procedure declared twice: " + symbolName);
		}
		procMap.put(symbolName, decl);
	}

	protected void addProcedureOrFunction(final String symbolName, final FunctionDeclaration decl) {
		addSymbol(StorageClass.PROC_FUNC, null, symbolName, decl);
	}

	protected void addInParams(final String procedureOrFunctionName, final String paramName, final Procedure decl) {
		if (isImplementation(decl)) {
			addSymbol(StorageClass.IMPLEMENTATION_INPARAM, procedureOrFunctionName, paramName, decl);
		} else {
			addSymbol(StorageClass.PROC_FUNC_INPARAM, procedureOrFunctionName, paramName, decl);
		}
	}

	protected void addInParams(final String procedureOrFunctionName, final String paramName,
			final FunctionDeclaration decl) {
		addSymbol(StorageClass.PROC_FUNC_INPARAM, procedureOrFunctionName, paramName, decl);
	}

	protected void addOutParams(final String procedureOrFunctionName, final String paramName, final Procedure decl) {
		if (isImplementation(decl)) {
			addSymbol(StorageClass.IMPLEMENTATION_OUTPARAM, procedureOrFunctionName, paramName, decl);
		} else {
			addSymbol(StorageClass.PROC_FUNC_OUTPARAM, procedureOrFunctionName, paramName, decl);
		}
	}

	protected void addOutParams(final String procedureOrFunctionName, final String paramName,
			final FunctionDeclaration decl) {
		addSymbol(StorageClass.PROC_FUNC_OUTPARAM, procedureOrFunctionName, paramName, decl);
	}

	protected void addLocalVariable(final String procedureName, final String variableName, final Declaration decl) {
		addSymbol(StorageClass.LOCAL, procedureName, variableName, decl);
	}

	public void addGlobalVariable(final String variableName, final Declaration decl) {
		addSymbol(StorageClass.GLOBAL, null, variableName, decl);
	}

	private Map<String, Declaration> getProcedureMap(final Procedure decl) {
		if (isImplementation(decl)) {
			return getMap(StorageClass.IMPLEMENTATION, StorageClass.IMPLEMENTATION.toString());
		} else {
			return getMap(StorageClass.PROC_FUNC, StorageClass.PROC_FUNC.toString());
		}
	}

	private boolean isImplementation(final Procedure decl) {
		return decl.getSpecification() == null;
	}

	private void addSymbol(final StorageClass sc, String scopeName, final String symbolName, final Declaration decl) {
		if (scopeName == null) {
			scopeName = sc.toString();
		}
		AssertIsEmpty(sc, scopeName, symbolName);
		getMap(sc, scopeName).put(symbolName, decl);
	}

	private void AssertIsEmpty(final StorageClass sc, final String scopeName, final String symbolName) {
		assert (!getMap(sc, scopeName).containsKey(symbolName)) : "duplicate symbol " + symbolName;
	}

	private Map<String, Declaration> getMap(final StorageClass sc, String scopeName) {
		scopeName = checkScopeName(sc, scopeName);

		switch (sc) {
			case IMPLEMENTATION:
			case PROC_FUNC:
			case GLOBAL:
			case QUANTIFIED:
				if (!mSymbolTable.containsKey(sc)) {
					final Map<String, Map<String, Declaration>> outer = new LinkedHashMap<String, Map<String, Declaration>>();
					final Map<String, Declaration> inner = new LinkedHashMap<String, Declaration>();
					outer.put(scopeName, inner);
					mSymbolTable.put(sc, outer);
				}
				return mSymbolTable.get(sc).get(scopeName);
			case PROC_FUNC_INPARAM:
			case PROC_FUNC_OUTPARAM:
			case IMPLEMENTATION_INPARAM:
			case IMPLEMENTATION_OUTPARAM:
			case LOCAL:
				if (!mSymbolTable.containsKey(sc)) {
					final Map<String, Map<String, Declaration>> outer = new LinkedHashMap<String, Map<String, Declaration>>();
					final Map<String, Declaration> inner = new LinkedHashMap<String, Declaration>();
					outer.put(scopeName, inner);
					mSymbolTable.put(sc, outer);
				}
				final Map<String, Map<String, Declaration>> scopeMap = mSymbolTable.get(sc);
				if (!scopeMap.containsKey(scopeName)) {
					scopeMap.put(scopeName, new LinkedHashMap<String, Declaration>());
				}
				return scopeMap.get(scopeName);
			default:
				throw new UnsupportedOperationException(String.format("Extend this method for the new scope %s", sc));
		}
	}

	private Collection<String> getSymbolNames(final StorageClass scope, String scopeName) {
		scopeName = checkScopeName(scope, scopeName);

		if (!mSymbolTable.containsKey(scope)) {
			return new ArrayList<String>();
		}
		final Map<String, Declaration> decls = getMap(scope, scopeName);
		if (decls == null) {
			return new ArrayList<String>();
		}
		final ArrayList<String> rtr = new ArrayList<>();
		rtr.addAll(decls.keySet());
		return rtr;
	}

	private String checkScopeName(final StorageClass scope, final String scopeName) {
		switch (scope) {
			case IMPLEMENTATION:
			case GLOBAL:
			case PROC_FUNC:
				// case QUANTIFIED:
				return scope.toString();
			default:
				break;
		}
		if (scopeName == null) {
			throw new IllegalArgumentException("scopeName must be non-null");
		}
		return scopeName;
	}

	/**
	 * Returns a list of declarations for the name of a function or procedure.
	 * <ul>
	 * <li>The list is emtpy if the symbol is not in the program</li>
	 * <li>The list contains one function declaration if the symbol is for a
	 * function</li>
	 * <li>For procedures, the list may contain up to two procedure declarations:
	 * One for the implementation, one for
	 * the specification.</li>
	 * </ul>
	 * 
	 * @param symbolname
	 * @return
	 */
	public List<Declaration> getFunctionOrProcedureDeclaration(final String symbolname) {
		final StorageClass[] procedures = new StorageClass[] { StorageClass.IMPLEMENTATION, StorageClass.PROC_FUNC };
		final ArrayList<Declaration> rtr = new ArrayList<>();
		for (final StorageClass sc : procedures) {
			final Declaration decl = getDeclaration(symbolname, sc, null);
			if (decl != null) {
				rtr.add(decl);
			}
		}
		return rtr;
	}

	public Map<String, Declaration> getGlobalVariables() {
		return new HashMap<>(getMap(StorageClass.GLOBAL, null));
	}

	public Map<String, Declaration> getLocalVariables(final String procedureName) {
		assert procedureName != null;
		final Map<String, Declaration> rtr = new HashMap<String, Declaration>();
		rtr.putAll(getMap(StorageClass.LOCAL, procedureName));
		rtr.putAll(getMap(StorageClass.IMPLEMENTATION_INPARAM, procedureName));
		rtr.putAll(getMap(StorageClass.IMPLEMENTATION_OUTPARAM, procedureName));
		rtr.putAll(getMap(StorageClass.PROC_FUNC_INPARAM, procedureName));
		rtr.putAll(getMap(StorageClass.PROC_FUNC_OUTPARAM, procedureName));
		return rtr;
	}

	/**
	 * 
	 * @param symbolname
	 * @param scope
	 * @param scopeName
	 * @return
	 */
	public Declaration getDeclaration(final String symbolname, final StorageClass scope, final String scopeName) {
		return getMap(scope, scopeName).get(symbolname);
	}

	public Declaration getDeclaration(final IdentifierExpression exp) {
		return getDeclaration(exp.getIdentifier(), exp.getDeclarationInformation().getStorageClass(),
				exp.getDeclarationInformation().getProcedure());
	}

	public IBoogieType getTypeForVariableSymbol(final String symbolname, final StorageClass scope,
			final String scopeName) {
		final Declaration decl = getDeclaration(symbolname, scope, scopeName);
		if (decl == null) {
			return null;
		}
		return findType(decl, symbolname);
	}

	private IBoogieType findType(final Declaration decl, final String symbolname) {
		if (decl instanceof VariableDeclaration) {
			return findType(((VariableDeclaration) decl).getVariables(), symbolname);
		} else if (decl instanceof Procedure) {
			final Procedure proc = (Procedure) decl;
			IBoogieType type = findType(proc.getInParams(), symbolname);
			if (type != null) {
				return type;
			}
			type = findType(proc.getOutParams(), symbolname);
			if (type != null) {
				return type;
			}
		}
		return null;
	}

	private IBoogieType findType(final VarList[] vlists, final String symbolname) {
		for (final VarList vl : vlists) {
			for (final String s : vl.getIdentifiers()) {
				if (s.equals(symbolname)) {
					return vl.getType().getBoogieType();
				}
			}
		}
		return null;
	}

	/**
	 * Produces a really long string describing the content of the symbol table.
	 * 
	 * @return A string representation of the symbol table.
	 */
	public String prettyPrintSymbolTable() {

		final StringBuilder globals = new StringBuilder();

		// global variables
		final Map<String, Declaration> globalsMap = getMap(StorageClass.GLOBAL, null);
		for (final String s : globalsMap.keySet()) {
			globals.append(" * ").append(s).append(" : ").append(getTypeForVariableSymbol(s, StorageClass.GLOBAL, null))
					.append("\n");
		}

		final HashSet<String> functionSymbols = new HashSet<>();
		functionSymbols.addAll(getSymbolNames(StorageClass.IMPLEMENTATION, null));
		functionSymbols.addAll(getSymbolNames(StorageClass.PROC_FUNC, null));

		final StringBuilder functions = new StringBuilder();
		final StringBuilder procedures = new StringBuilder();
		final StringBuilder implementations = new StringBuilder();

		// functions and procedures, inlined with local definitions
		for (final String functionSymbol : functionSymbols) {
			// get the declaration(s) for the function or procedure symbol
			final List<Declaration> decls = getFunctionOrProcedureDeclaration(functionSymbol);

			for (final Declaration decl : decls) {
				// check what kind of symbol it is
				if (decl instanceof FunctionDeclaration) {
					functions.append(" * ").append(functionSymbol).append(" := ").append(decl).append("\n");
					// add the local variable declarations
					appendLocals(functions, functionSymbol);
				} else {
					final Procedure proc = (Procedure) decl;
					if (isImplementation(proc)) {
						// implementations.append(" * ").append(functionSymbol).append(" :=
						// ").append(decl).append("\n");
						implementations.append(" * ").append(prettyPrintProcedureSignature(decl)).append("\n");
						appendLocals(implementations, functionSymbol);
					} else {
						// procedures.append(" * ").append(functionSymbol).append(" :=
						// ").append(decl).append("\n");
						procedures.append(" * ").append(prettyPrintProcedureSignature(decl)).append("\n");
						if (decls.size() == 1) {
							// only print locals if there is no implementation
							// defined (do not print locals 2 times)
							appendLocals(procedures, functionSymbol);
						}
					}
				}
			}
		}

		final StringBuilder sb = new StringBuilder();
		if (globals.length() > 0) {
			sb.append("Globals\n");
			sb.append(globals);
			sb.append("\n");
		}

		if (procedures.length() > 0) {
			sb.append("Procedures\n");
			sb.append(procedures);
			sb.append("\n");
		}

		if (implementations.length() > 0) {
			sb.append("Implementations\n");
			sb.append(implementations);
			sb.append("\n");
		}

		if (functions.length() > 0) {
			sb.append("Functions\n");
			sb.append(functions);
			sb.append("\n");
		}

		return sb.toString();

	}

	private void appendLocals(final StringBuilder builder, final String currentFunctionSymbol) {
		appendLocals(StorageClass.PROC_FUNC_INPARAM, builder, currentFunctionSymbol);
		appendLocals(StorageClass.PROC_FUNC_OUTPARAM, builder, currentFunctionSymbol);
		appendLocals(StorageClass.IMPLEMENTATION_INPARAM, builder, currentFunctionSymbol);
		appendLocals(StorageClass.IMPLEMENTATION_OUTPARAM, builder, currentFunctionSymbol);
		appendLocals(StorageClass.LOCAL, builder, currentFunctionSymbol);
	}

	private void appendLocals(final StorageClass scClass, final StringBuilder builder,
			final String currentFunctionSymbol) {
		final Collection<String> localSymbols = getSymbolNames(scClass, currentFunctionSymbol);
		if (localSymbols.isEmpty()) {
			return;
		}
		for (final String symbol : localSymbols) {
			final IBoogieType type = getTypeForVariableSymbol(symbol, scClass, currentFunctionSymbol);
			builder.append("  * ").append(shorten(scClass)).append(" ").append(symbol).append(" : ").append(type)
					.append("\n");
		}
	}

	private String shorten(final StorageClass scClass) {
		switch (scClass) {
			case GLOBAL:
				return "G";
			case IMPLEMENTATION:
				return "IMPL";
			case IMPLEMENTATION_INPARAM:
				return "I_IN";
			case IMPLEMENTATION_OUTPARAM:
				return "I_OUT";
			case LOCAL:
				return "LOC";
			case PROC_FUNC:
				return "PF";
			case PROC_FUNC_INPARAM:
				return "PF_IN";
			case PROC_FUNC_OUTPARAM:
				return "PF_OUT";
			case QUANTIFIED:
				return "Q";
			default:
				return "UNKNOWN";
		}
	}

	private String prettyPrintProcedureSignature(final Declaration decl) {
		final PrettyPrinter signatureBuilder = new PrettyPrinter();
		try {
			return signatureBuilder.process(decl).toString();
		} catch (final Throwable e) {
			e.printStackTrace();
			return "";
		}
	}

	private static final class PrettyPrinter extends BoogieVisitor {
		private StringBuilder mBuilder;

		public StringBuilder process(final Declaration node) {
			mBuilder = new StringBuilder();
			processDeclaration(node);
			// replace the superfluous " returns " at the end (from
			// processVarLists)
			mBuilder.replace(mBuilder.length() - 9, mBuilder.length(), "");
			return mBuilder;
		}

		@Override
		protected void visit(final Procedure decl) {
			mBuilder.append(decl.getIdentifier());
		}

		@Override
		protected void visit(final FunctionDeclaration decl) {
			mBuilder.append(decl.getIdentifier());
		}

		@Override
		protected VarList[] processVarLists(final VarList[] vls) {
			mBuilder.append("(");
			final VarList[] rtr = super.processVarLists(vls);
			if (vls.length > 0) {
				// replace the superfluous ", "
				mBuilder.replace(mBuilder.length() - 2, mBuilder.length(), "");
			}
			mBuilder.append(") returns ");
			return rtr;
		}

		@Override
		protected VarList processVarList(final VarList vl) {
			final String[] identifiers = vl.getIdentifiers();
			if (identifiers.length > 0) {
				for (final String name : identifiers) {
					mBuilder.append(name).append(" : ").append(vl.getType().getBoogieType()).append(", ");
				}
			}
			return super.processVarList(vl);
		}

		// prevent traversing the whole ast with the following overrides
		@Override
		protected Body processBody(final Body body) {
			return body;
		}

		@Override
		protected Expression processExpression(final Expression expr) {
			return expr;
		}
	}

	/**
	 * Get mapping from Boogie function names to SMT function names.
	 * This extracts function mappings from the symbol table for SMT conversion.
	 * 
	 * @return Map from Boogie function identifier to SMT function identifier
	 */
	public Map<String, String> getBoogieFunction2SmtFunction() {
		Map<String, String> result = new HashMap<>();

		// Get all function and procedure symbols
		Set<String> functionSymbols = new HashSet<>();
		functionSymbols.addAll(getSymbolNames(StorageClass.IMPLEMENTATION, null));
		functionSymbols.addAll(getSymbolNames(StorageClass.PROC_FUNC, null));

		for (String functionSymbol : functionSymbols) {
			List<Declaration> decls = getFunctionOrProcedureDeclaration(functionSymbol);

			for (Declaration decl : decls) {
				if (decl instanceof FunctionDeclaration) {
					FunctionDeclaration funcDecl = (FunctionDeclaration) decl;
					String boogieId = funcDecl.getIdentifier();

					// Check for SMT mapping attributes
					String smtName = extractSmtFunctionName(funcDecl);
					if (smtName != null) {
						result.put(boogieId, smtName);
					} else {
						// Default mapping: use same name
						result.put(boogieId, boogieId);
					}
				} else if (decl instanceof Procedure) {
					Procedure procDecl = (Procedure) decl;
					String boogieId = procDecl.getIdentifier();

					// Check for SMT mapping attributes
					String smtName = extractSmtFunctionName(procDecl);
					if (smtName != null) {
						result.put(boogieId, smtName);
					} else {
						// Default mapping: use same name
						result.put(boogieId, boogieId);
					}
				}
			}
		}

		return result;
	}

	/**
	 * Get mapping from SMT function names to Boogie function names.
	 * This is the reverse of getBoogieFunction2SmtFunction().
	 * 
	 * @return Map from SMT function identifier to Boogie function identifier
	 */
	public Map<String, String> getSmtFunction2BoogieFunction() {
		Map<String, String> boogieToSmt = getBoogieFunction2SmtFunction();
		Map<String, String> result = new HashMap<>();

		for (Map.Entry<String, String> entry : boogieToSmt.entrySet()) {
			result.put(entry.getValue(), entry.getKey());
		}

		return result;
	}

	/**
	 * Extract SMT function name from function/procedure attributes.
	 * Looks for builtin or smtdefined attributes that specify the SMT name.
	 * 
	 * @param decl The function or procedure declaration
	 * @return SMT function name if specified in attributes, null otherwise
	 */
	private String extractSmtFunctionName(Declaration decl) {
		if (decl.getAttributes() == null) {
			return null;
		}

		for (Attribute attr : decl.getAttributes()) {
			if (attr instanceof NamedAttribute) {
				NamedAttribute namedAttr = (NamedAttribute) attr;
				String attrName = namedAttr.getName();

				// Check for builtin attribute
				if ("builtin".equals(attrName)) {
					Expression[] values = namedAttr.getValues();
					if (values != null && values.length > 0 && values[0] instanceof StringLiteral) {
						StringLiteral smtName = (StringLiteral) values[0];
						return smtName.getValue();
					}
				}

				// Check for smtdefined attribute
				if ("smtdefined".equals(attrName)) {
					Expression[] values = namedAttr.getValues();
					if (values != null && values.length > 0 && values[0] instanceof StringLiteral) {
						StringLiteral smtName = (StringLiteral) values[0];
						return smtName.getValue();
					}
				}
			}
		}

		return null;
	}

	/**
	 * Get all global variable declarations with their types.
	 * 
	 * @return Map from variable name to Boogie type string
	 */
	public Map<String, String> getGlobalVariableTypes() {
		Map<String, String> result = new HashMap<>();
		Map<String, Declaration> globalsMap = getMap(StorageClass.GLOBAL, null);

		for (String varName : globalsMap.keySet()) {
			IBoogieType type = getTypeForVariableSymbol(varName, StorageClass.GLOBAL, null);
			if (type != null) {
				result.put(varName, convertBoogieTypeToSMT(type));
			}
		}

		return result;
	}

	/**
	 * Get all constant declarations with their types and values.
	 * 
	 * @return Map from constant name to "type:value" string
	 */
	public Map<String, String> getConstantDeclarations() {
		Map<String, String> result = new HashMap<>();

		// Add built-in constants
		result.put("null", "Int:0");
		result.put("true", "Bool:true");
		result.put("false", "Bool:false");

		// TODO: Extract actual constants from symbol table if needed
		// This would require iterating through const declarations

		return result;
	}

	/**
	 * Convert Boogie type to SMT type string.
	 * 
	 * @param boogieType The Boogie type to convert
	 * @return SMT type string
	 */
	private String convertBoogieTypeToSMT(IBoogieType boogieType) {
		if (boogieType instanceof BoogiePrimitiveType) {
			BoogiePrimitiveType primType = (BoogiePrimitiveType) boogieType;
			switch (primType.getTypeCode()) {
				case BoogiePrimitiveType.INT:
					return "Int";
				case BoogiePrimitiveType.REAL:
					return "Real";
				case BoogiePrimitiveType.BOOL:
					return "Bool";
				default:
					return "Int"; // Default fallback
			}
		} else if (boogieType instanceof BoogieArrayType) {
			BoogieArrayType arrayType = (BoogieArrayType) boogieType;
			// Simplified array type conversion
			return "(Array Int Int)"; // Most common case
		}

		return "Int"; // Default fallback
	}
}
