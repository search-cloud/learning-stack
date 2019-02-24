package io.vincent.compiler.ir;
import io.vincent.compiler.asm.Type;

/**
 * 一元运算表达式
 */
public class Unary extends Expression {
    // 运算符
    protected Operator operator;
    // 表达式
    protected Expression expression;

    public Unary(Type type, Operator operator, Expression expression) {
        super(type);
        this.operator = operator;
        this.expression = expression;
    }

    public Operator op() { return operator; }
    public Expression expr() { return expression; }

    public <S,E> E accept(IRVisitor<S,E> visitor) {
        return visitor.visit(this);
    }

    public void _dump(Dumper d) {
        d.printMember("op", operator.toString());
        d.printMember("expression", expression);
    }
}
