/*
 * Copyright (C) 2015 Thomas Lang
 * Copyright (C) 2015 University of Freiburg
 *
 * This file is part of the ULTIMATE ModelCheckerUtils Library.
 *
 * The ULTIMATE ModelCheckerUtils Library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ULTIMATE ModelCheckerUtils Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ULTIMATE ModelCheckerUtils Library. If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional permission under GNU GPL version 3 section 7:
 * If you modify the ULTIMATE ModelCheckerUtils Library, or any covered work, by linking
 * or combining it with Eclipse RCP (or a modified version of Eclipse RCP),
 * containing parts covered by the terms of the Eclipse Public License, the
 * licensors of the ULTIMATE ModelCheckerUtils Library grant you additional permission
 * to convey the resulting work.
 */
package ultimate.util.boogie;

import java.math.BigInteger;
import java.util.Collection;
import java.util.logging.Logger;

import ultimate.ast.BoogieASTNode;
import ultimate.ast.TypeDeclaration;
import ultimate.logic.Script;
import ultimate.logic.Sort;
import ultimate.models.IBoogieType;
import ultimate.type.BoogieType;
import ultimate.util.smt.SmtSortUtils;

/**
 * Translate integers to bit vectors, otherwise call TypeSortTranslator.
 *
 * @author Thomas Lang
 * @author TOTO (Towzer)
 *
 */
public class TypeSortTranslatorBitvectorWorkaround extends TypeSortTranslator {

	public TypeSortTranslatorBitvectorWorkaround(final Collection<TypeDeclaration> declarations, final Script script,
			final Logger logger) {
		super(declarations, script, logger);
	}

	@Override
	protected Sort constructSort(final IBoogieType boogieType, final BoogieASTNode BoogieASTNode) {
		if (boogieType.equals(BoogieType.TYPE_INT)) {
			final BigInteger[] sortIndices = { BigInteger.valueOf(32) };
			return SmtSortUtils.getBitvectorSort(mScript, sortIndices);
		}
		return super.constructSort(boogieType, BoogieASTNode);
	}
}
