package io.vincent.compiler.c.ir;

/**
 * Interpretation Representation Visitor.
 *
 * @param <S> the type of the statement.
 * @param <E> the type of the expression.
 */
public interface IRVisitor<S, E> {
	S visit(ExpressionStatement s);

	S visit(Assign s);

	S visit(CJump s);

	S visit(Jump s);

	S visit(Switch s);

	S visit(LabelStatement s);

	S visit(Return s);

	E visit(Unary s);

	E visit(Binary s);

	E visit(Call s);

	E visit(Addr s);

	E visit(Mem s);

	E visit(Var s);

	E visit(Int s);

	E visit(Str s);
}
