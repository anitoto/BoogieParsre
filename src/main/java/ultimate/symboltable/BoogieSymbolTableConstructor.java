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

import java.util.logging.Logger;

import ultimate.ast.BoogieOnceVisitor;
import ultimate.ast.ConstDeclaration;
import ultimate.ast.Declaration;
import ultimate.ast.DeclarationInformation.StorageClass;
import ultimate.ast.FunctionDeclaration;
import ultimate.ast.Procedure;
import ultimate.ast.Unit;
import ultimate.ast.VarList;
import ultimate.ast.VariableDeclaration;
import ultimate.models.IElement;
import ultimate.models.annotation.PreprocessorAnnotation;
import ultimate.models.observers.IUnmanagedObserver;

/**
 *
 * @author Daniel Dietsch (dietsch@informatik.uni-freiburg.de)
 * @author TOTO (Towzer)
 *
 */
public class BoogieSymbolTableConstructor extends BoogieOnceVisitor implements IUnmanagedObserver {
	// Global Java logger
	private final Logger logger;

	private BoogieSymbolTable mSymbolTable;
	private Unit mRootNode;
	private StorageClass mCurrentScope;
	private Declaration mCurrentDeclaration;
	private String mCurrentScopeName;

	public BoogieSymbolTableConstructor(final Logger logger) {
		this.logger = logger;
		mSymbolTable = new BoogieSymbolTable();
	}

	@Override
	public void init() throws Throwable {
		mCurrentScope = StorageClass.GLOBAL;
		mCurrentDeclaration = null;
		mCurrentScopeName = null;
		mRootNode = null;
	}

	@Override
	public void finish() throws Throwable {
		final PreprocessorAnnotation pa = new PreprocessorAnnotation();
		pa.setSymbolTable(mSymbolTable);
		pa.annotate(mRootNode);
		logger.fine("SymbolTable\r" + mSymbolTable.prettyPrintSymbolTable());
	}

	@Override
	public boolean performedChanges() {
		return false;
	}

	public BoogieSymbolTable getSymbolTable() {
		return mSymbolTable;

	}

	@Override
	public boolean process(final IElement root) throws Throwable {
		if (root instanceof Unit) {
			return process((Unit) root);
		}
		return true;
	}

	public Boolean process(final Unit node) throws Throwable {
		mRootNode = node;
		for (final Declaration decl : mRootNode.getDeclarations()) {
			if (decl instanceof VariableDeclaration || decl instanceof ConstDeclaration) {
				mCurrentScope = StorageClass.GLOBAL;
				mCurrentDeclaration = decl;
			}
			processDeclaration(decl);
		}
		return false;
	}

	@Override
	protected void visit(final FunctionDeclaration decl) {
		mCurrentDeclaration = decl;
		mCurrentScope = StorageClass.PROC_FUNC;
		mCurrentScopeName = decl.getIdentifier();
		mSymbolTable.addProcedureOrFunction(decl.getIdentifier(), decl);

		if (decl.getInParams() != null) {
			for (final VarList vl : decl.getInParams()) {
				for (final String name : vl.getIdentifiers()) {
					mSymbolTable.addInParams(decl.getIdentifier(), name, decl);
				}
			}
		}

		if (decl.getOutParam() != null) {
			for (final String name : decl.getOutParam().getIdentifiers()) {
				mSymbolTable.addOutParams(decl.getIdentifier(), name, decl);
			}
		}

		super.visit(decl);
	}

	@Override
	protected void visit(final Procedure decl) {
		mCurrentDeclaration = decl;
		mCurrentScope = StorageClass.PROC_FUNC;
		mCurrentScopeName = decl.getIdentifier();
		mSymbolTable.addProcedureOrFunction(decl.getIdentifier(), decl);

		if (decl.getInParams() != null) {
			for (final VarList vl : decl.getInParams()) {
				for (final String name : vl.getIdentifiers()) {
					mSymbolTable.addInParams(decl.getIdentifier(), name, decl);
				}
			}
		}

		if (decl.getOutParams() != null) {
			for (final VarList vl : decl.getOutParams()) {
				for (final String name : vl.getIdentifiers()) {
					mSymbolTable.addOutParams(decl.getIdentifier(), name, decl);
				}
			}
		}

		// TODO What about type params?
		super.visit(decl);
	}

	@Override
	protected VariableDeclaration processLocalVariableDeclaration(final VariableDeclaration local) {
		mCurrentDeclaration = local;
		mCurrentScope = StorageClass.LOCAL;
		return super.processLocalVariableDeclaration(local);
	}

	@Override
	protected VarList processVarList(final VarList vl) {
		switch (mCurrentScope) {
			case LOCAL:
				for (final String name : vl.getIdentifiers()) {
					mSymbolTable.addLocalVariable(mCurrentScopeName, name, mCurrentDeclaration);
				}
				break;
			case GLOBAL:
				for (final String name : vl.getIdentifiers()) {
					mSymbolTable.addGlobalVariable(name, mCurrentDeclaration);
				}
				break;
			case PROC_FUNC:
				break;
			default:
				throw new UnsupportedOperationException(String.format("Extend this method for the new scope %s",
						mCurrentScope));
		}
		return super.processVarList(vl);
	}

}
