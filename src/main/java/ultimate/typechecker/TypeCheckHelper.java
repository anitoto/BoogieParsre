/*
 * Copyright (C) 2008-2015 Jochen Hoenicke (hoenicke@informatik.uni-freiburg.de)
 * Copyright (C) 2018 Alexander Nutz (nutz@informatik.uni-freiburg.de)
 * Copyright (C) 2015-2018 University of Freiburg
 *
 * This file is part of the ULTIMATE BoogieAST library.
 *
 * The ULTIMATE BoogieAST library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ULTIMATE BoogieAST library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ULTIMATE BoogieAST library. If not, see <http://www.gnu.org/licenses/>.
 *
 * Additional permission under GNU GPL version 3 section 7:
 * If you modify the ULTIMATE BoogieAST library, or any covered work, by linking
 * or combining it with Eclipse RCP (or a modified version of Eclipse RCP),
 * containing parts covered by the terms of the Eclipse Public License, the
 * licensors of the ULTIMATE BoogieAST library grant you additional permission
 * to convey the resulting work.
 */
package ultimate.typechecker;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import ultimate.ast.ArrayLHS;
import ultimate.ast.BinaryExpression;
import ultimate.ast.Expression;
import ultimate.ast.ExpressionFactory;
import ultimate.ast.LeftHandSide;
import ultimate.ast.StructLHS;
import ultimate.ast.UnaryExpression.Operator;
import ultimate.ast.VariableLHS;
import ultimate.type.BoogieArrayType;
import ultimate.type.BoogiePrimitiveType;
import ultimate.type.BoogieStructType;
import ultimate.type.BoogieType;

/**
 * Contains methods that infer the Boogie type for any kind of composite Boogie
 * expression from its component's types.
 *
 * This code was factored our from the {@link TypeChecker} in BoogiePreprocessor
 * in order to make it available to the
 * {@link ExpressionFactory}.
 *
 * @author Alexander Nutz (nutz@informatik.uni-freiburg.de)
 * @author TOTO (Towzer)
 *
 */
public class TypeCheckHelper {

	private TypeCheckHelper() {
		// don't instantiate this
	}

	public static <T> BoogieType typeCheckArrayAccessExpressionOrLhs(
			final BoogieType arrayType,
			final List<BoogieType> indicesTypes,
			final String expr,
			final Logger logger) {
		BoogieType resultType;
		if (!(arrayType instanceof BoogieArrayType)) {
			if (!BoogieType.TYPE_ERROR.equals(arrayType)) {
				logger.severe("Type check failed (not an array): " + expr);
			}
			resultType = BoogieType.TYPE_ERROR;
		} else {
			final BoogieArrayType arr = (BoogieArrayType) arrayType;
			final BoogieType[] subst = new BoogieType[arr.getNumPlaceholders()];
			if (indicesTypes.size() != arr.getIndexCount()) {
				logger.severe("Type check failed (wrong number of indices): " + expr);
			} else {
				for (int i = 0; i < indicesTypes.size(); i++) {
					final BoogieType t = indicesTypes.get(i);
					if (!BoogieType.TYPE_ERROR.equals(t) && !arr.getIndexType(i).unify(t, subst)) {
						final int index = i;
						logger.severe("Type check failed (index " + index + "): " + expr);
					}
				}
			}
			resultType = arr.getValueType().substitutePlaceholders(subst);
		}
		return resultType;
	}

	public static <T> BoogieType typeCheckStructAccessExpressionOrLhs(
			final BoogieType structType,
			final String accessedField,
			final String expr,
			final Logger logger) {
		BoogieType resultType;
		if (!(structType instanceof BoogieStructType)) {
			if (!BoogieType.TYPE_ERROR.equals(structType)) {
				logger.severe("Type check failed (not a struct): " + expr);
			}
			resultType = BoogieType.TYPE_ERROR;
		} else {
			final BoogieStructType str = (BoogieStructType) structType;
			resultType = null;
			for (int i = 0; i < str.getFieldCount(); i++) {
				final String fieldName = str.getFieldIds()[i];
				if (fieldName.equals(accessedField)) {
					resultType = str.getFieldType(i);
					if (resultType == null) {
						logger.severe("Type check failed (field " + fieldName + " not in struct): " + expr);
						resultType = BoogieType.TYPE_ERROR;
						break;
					}
				}
			}

		}
		return resultType;
	}

	public static <T> BoogieType typeCheckBitVectorAccessExpression(
			final int bvlen,
			int end,
			int start,
			final BoogieType bvType,
			final Expression expr,
			final Logger logger) {
		BoogieType resultType;
		if (start < 0 || end < start || bvlen < end) {
			if (!BoogieType.TYPE_ERROR.equals(bvType)) {
				logger.severe("Type check failed: " + expr);
			}
			start = end = 0;
		}
		resultType = BoogieType.createBitvectorType(end - start);
		return resultType;
	}

	public static <T> BoogieType typeCheckUnaryExpression(
			final Operator op,
			final BoogieType subtype,
			final Expression expr,
			final Logger logger) {
		BoogieType resultType;
		switch (op) {
			case LOGICNEG:
				if (!BoogieType.TYPE_ERROR.equals(subtype) && !BoogieType.TYPE_BOOL.equals(subtype)) {
					logger.severe("Type check failed: " + expr);
				}
				/* try to recover in any case */
				resultType = BoogieType.TYPE_BOOL;
				break;
			case ARITHNEGATIVE:
				if (!BoogieType.TYPE_ERROR.equals(subtype) && !BoogieType.TYPE_INT.equals(subtype)
						&& !BoogieType.TYPE_REAL.equals(subtype)) {
					logger.severe("Type check failed: " + expr);
				}
				resultType = subtype;
				break;
			case OLD:
				resultType = subtype;
				break;
			default:
				internalError("Unknown Unary operator " + op);
				resultType = BoogieType.TYPE_ERROR;
				break;
		}
		return resultType;
	}

	public static <T> BoogieType typeCheckBinaryExpression(
			final BinaryExpression.Operator op,
			final BoogieType leftType,
			final BoogieType rightType,
			final String expr,
			final Logger logger) {
		BoogieType resultType;
		BoogieType left = leftType;
		BoogieType right = rightType;

		switch (op) {
			case LOGICIFF:
			case LOGICIMPLIES:
			case LOGICAND:
			case LOGICOR:
				if (!BoogieType.TYPE_ERROR.equals(left) && !BoogieType.TYPE_BOOL.equals(left)
						|| !BoogieType.TYPE_ERROR.equals(right) && !BoogieType.TYPE_BOOL.equals(right)) {
					logger.severe("Type check failed: " + expr);
				}
				/* try to recover in any case */
				resultType = BoogieType.TYPE_BOOL;
				break;
			case ARITHDIV:
			case ARITHMINUS:
			case ARITHMOD:
			case ARITHMUL:
			case ARITHPLUS:
				/* Try to recover for error types */
				if (BoogieType.TYPE_ERROR.equals(left)) {
					left = right;
				} else if (BoogieType.TYPE_ERROR.equals(right)) {
					right = left;
				}
				if (!right.equals(left) || !BoogieType.TYPE_INT.equals(left) && !BoogieType.TYPE_REAL.equals(left)
						|| BoogieType.TYPE_REAL.equals(left) && op == BinaryExpression.Operator.ARITHMOD) {
					logger.severe("Type check failed: " + expr);
					resultType = BoogieType.TYPE_ERROR;
				} else {
					resultType = left;
				}
				break;
			case COMPLT:
			case COMPGT:
			case COMPLEQ:
			case COMPGEQ:
				/* Try to recover for error types */
				if (BoogieType.TYPE_ERROR.equals(left)) {
					left = right;
				} else if (BoogieType.TYPE_ERROR.equals(right)) {
					right = left;
				}

				if (!Objects.equals(left, right)
						|| !BoogieType.TYPE_INT.equals(left) && !BoogieType.TYPE_REAL.equals(left)) {
					logger.severe("Type check failed: " + expr);
				}
				/* try to recover in any case */
				resultType = BoogieType.TYPE_BOOL;
				break;
			case COMPNEQ:
			case COMPEQ:
				if (!left.isUnifiableTo(right)) {
					final String msg = left + " is not unifiable to " + right;
					logger.severe(msg + ": " + expr);
				}
				/* try to recover in any case */
				resultType = BoogieType.TYPE_BOOL;
				break;
			case COMPPO:
				if (!Objects.equals(left, right) && !BoogieType.TYPE_ERROR.equals(left)
						&& !BoogieType.TYPE_ERROR.equals(right)) {
					logger.severe("Type check failed "
							+ leftType.getUnderlyingType() + " != " + rightType.getUnderlyingType()
							+ ": " + expr);
				}
				/* try to recover in any case */
				resultType = BoogieType.TYPE_BOOL;
				break;
			case BITVECCONCAT:
				int leftLen = getBitVecLength(left);
				int rightLen = getBitVecLength(right);
				if (leftLen < 0 || rightLen < 0 || leftLen + rightLen < 0) {
					// handle overflow
					if (!BoogieType.TYPE_ERROR.equals(left) && !BoogieType.TYPE_ERROR.equals(right)) {
						logger.severe("Type check failed: " + expr);
					}
					// recover
					leftLen = 0;
					rightLen = 0;
				}
				resultType = BoogieType.createBitvectorType(leftLen + rightLen);
				break;
			default:
				internalError("Unknown Binary operator " + op);
				resultType = BoogieType.TYPE_ERROR;
				break;
		}
		return resultType;
	}

	public static <T> BoogieType typeCheckIfThenElseExpression(
			final BoogieType condType,
			final BoogieType left,
			final BoogieType right,
			final Expression condition,
			final Logger logger) {
		BoogieType resultType;
		if (!condType.equals(BoogieType.TYPE_ERROR) && !condType.equals(BoogieType.TYPE_BOOL)) {
			logger.severe("if expects boolean type: " + condition);
		}
		if (!left.isUnifiableTo(right)) {
			logger.severe("Type check failed " + left + " != " + right);
			resultType = BoogieType.TYPE_ERROR;
		} else {
			resultType = left.equals(BoogieType.TYPE_ERROR) ? right : left;
		}
		return resultType;
	}

	public static <T> void typeCheckAssignStatement(
			final String[] lhsIds,
			final BoogieType[] lhsTypes,
			final BoogieType[] rhsTypes,
			final String expr,
			final Logger logger) {
		// if (lhs.length != rhs.length) {
		if (lhsTypes.length != rhsTypes.length) {
			// typeError(statement, "Number of variables do not match in: " + statement);
			logger.severe("Number of variables do not match in: " + expr);
		} else {
			for (int i = 0; i < lhsTypes.length; i++) {
				// lhsId[i] = getLeftHandSideIdentifier(lhs[i]);
				for (int j = 0; j < i; j++) {
					if (lhsIds[i].equals(lhsIds[j])) {
						// typeError(statement, "Variable appears multiple times in assignment in: " +
						// statement);
						logger.severe("Variable appears multiple times in assignment in: " + expr);
					}
				}
				final BoogieType lhsType = lhsTypes[i];// typecheckLeftHandSide(lhs[i]);
				final BoogieType rhsType = rhsTypes[i];// typecheckExpression(rhs[i]);
				if (!BoogieType.TYPE_ERROR.equals(lhsType) && !BoogieType.TYPE_ERROR.equals(rhsType)
						&& !lhsType.equals(rhsType)) {
					// typeError(statement, "Type mismatch (" + lhsType + " != " + rhsType + ") in:
					// " + statement);
					logger.severe("Type mismatch (" + lhsType + " != " + rhsType + ") in: " + expr);
				}
			}
		}
	}

	public static void internalError(final String message) {
		throw new AssertionError(message);
	}

	public static int getBitVecLength(BoogieType t) {
		t = t.getUnderlyingType();
		if (!(t instanceof BoogiePrimitiveType)) {
			return -1;
		}
		return ((BoogiePrimitiveType) t).getTypeCode();
	}

	public static <T> BoogieType typeCheckArrayStoreExpression(
			final BoogieType arrayType,
			final List<BoogieType> indicesTypes,
			final BoogieType valueType,
			final Expression expr,
			final Logger logger) {
		BoogieType resultType;
		if (!(arrayType instanceof BoogieArrayType)) {
			if (!BoogieType.TYPE_ERROR.equals(arrayType)) {
				logger.severe("Type check failed (not an array): " + expr);
			}
			resultType = BoogieType.TYPE_ERROR;
		} else {
			final BoogieArrayType arr = (BoogieArrayType) arrayType;
			final BoogieType[] subst = new BoogieType[arr.getNumPlaceholders()];
			if (indicesTypes.size() != arr.getIndexCount()) {
				logger.severe("Type check failed (wrong number of indices): " + expr);
			} else {
				for (int i = 0; i < indicesTypes.size(); i++) {
					// final BoogieType t = typecheckExpression(indices[i]);
					final BoogieType t = indicesTypes.get(i);// typecheckExpression(indices[i]);
					if (!BoogieType.TYPE_ERROR.equals(t) && !arr.getIndexType(i).unify(t, subst)) {
						// typeError(expr, "Type check failed (index " + i + "): " + statement);
						final int index = i;
						logger.severe("Type check failed (index " + index + "): " + expr);
					}
				}
				if (!BoogieType.TYPE_ERROR.equals(valueType) && !arr.getValueType().unify(valueType, subst)) {
					// typeError(expr, "Type check failed (value): " + statement);
					logger.severe("Type check failed (value): " + expr);
				}
			}
			resultType = arr;
		}
		return resultType;
	}

	public static String getLeftHandSideIdentifier(LeftHandSide lhs) {
		while (lhs instanceof ArrayLHS || lhs instanceof StructLHS) {
			if (lhs instanceof ArrayLHS) {
				lhs = ((ArrayLHS) lhs).getArray();
			} else if (lhs instanceof StructLHS) {
				lhs = ((StructLHS) lhs).getStruct();
			}
		}
		return ((VariableLHS) lhs).getIdentifier();
	}

}
