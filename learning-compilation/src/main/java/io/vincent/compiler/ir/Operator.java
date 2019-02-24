package io.vincent.compiler.ir;

/**
 * Operator.
 */
public enum Operator {
	ADD,
	SUB,
	MUL,
	S_DIV,
	U_DIV,
	S_MOD,
	U_MOD,
	BIT_AND,
	BIT_OR,
	BIT_XOR,
	BIT_LSHIFT,
	BIT_RSHIFT,
	ARITH_RSHIFT,

	EQ,
	NEQ,
	S_GT,
	S_GTEQ,
	S_LT,
	S_LTEQ,
	U_GT,
	U_GTEQ,
	U_LT,
	U_LTEQ,

	UMINUS,
	BIT_NOT,
	NOT,

	S_CAST,
	U_CAST;

	/**
	 * Get semantic type.
	 *
	 * @param op       operator's source.
	 * @param isSigned is singed.
	 * @return semitic Operator.
	 */
	public static Operator internBinary(String op, boolean isSigned) {
		switch (op) {
			case "+":
				return Operator.ADD;
			case "-":
				return Operator.SUB;
			case "*":
				return Operator.MUL;
			case "/":
				return isSigned ? Operator.S_DIV : Operator.U_DIV;
			case "%":
				return isSigned ? Operator.S_MOD : Operator.U_MOD;
			case "&":
				return Operator.BIT_AND;
			case "|":
				return Operator.BIT_OR;
			case "^":
				return Operator.BIT_XOR;
			case "<<":
				return Operator.BIT_LSHIFT;
			case ">>":
				return isSigned ? Operator.ARITH_RSHIFT : Operator.BIT_RSHIFT;
			case "==":
				return Operator.EQ;
			case "!=":
				return Operator.NEQ;
			case "<":
				return isSigned ? Operator.S_LT : Operator.U_LT;
			case "<=":
				return isSigned ? Operator.S_LTEQ : Operator.U_LTEQ;
			case ">":
				return isSigned ? Operator.S_GT : Operator.U_GT;
			case ">=":
				return isSigned ? Operator.S_GTEQ : Operator.U_GTEQ;
			default:
				throw new Error("unknown binary op: " + op);
		}
	}

	/**
	 * Get asm type.
	 *
	 * @param op operator's source.
	 * @return asm Operator.
	 */
	public static Operator internUnary(String op) {
		switch (op) {
			case "+":
				throw new Error("unary+ should not be in IR");
			case "-":
				return Operator.UMINUS;
			case "~":
				return Operator.BIT_NOT;
			case "!":
				return Operator.NOT;
			default:
				throw new Error("unknown unary op: " + op);
		}
	}
}
