package io.vincent.compiler.c.ir;
import io.vincent.compiler.c.ast.Location;

/**
 * 带有表达式的语句
 */
public class ExpressionStatement extends Statement {
    // 表达式
    protected Expression expression;

    public ExpressionStatement(Location loc, Expression expression) {
        super(loc);
        this.expression = expression;
    }

    public Expression expr() {
        return expression;
    }

    public <S,E> S accept(IRVisitor<S,E> visitor) {
        return visitor.visit(this);
    }

    public void _dump(Dumper d) {
        d.printMember("expression", expression);
    }
}
