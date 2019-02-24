package io.vincent.compiler.ir;
import io.vincent.compiler.ast.Location;

/**
 * 赋值语句
 */
public class Assign extends Statement {
    /**
     * 赋值语句，左右表达式
     */
    protected Expression lhs, rhs;

    public Assign(Location loc, Expression lhs, Expression rhs) {
        super(loc);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Expression lhs() {
        return lhs;
    }

    public Expression rhs() {
        return rhs;
    }

    public <S,E> S accept(IRVisitor<S,E> visitor) {
        return visitor.visit(this);
    }

    public void _dump(Dumper d) {
        d.printMember("lhs", lhs);
        d.printMember("rhs", rhs);
    }
}
