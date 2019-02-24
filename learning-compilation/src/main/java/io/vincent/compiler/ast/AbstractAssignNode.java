package io.vincent.compiler.ast;
import io.vincent.compiler.context.Dumper;
import io.vincent.compiler.type.*;

 public abstract class AbstractAssignNode extends ExpressionNode {
    ExpressionNode lhs, rhs;

    public AbstractAssignNode(ExpressionNode lhs, ExpressionNode rhs) {
        super();
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Type type() {
        return lhs.type();
    }

    public ExpressionNode lhs() {
        return lhs;
    }

    public ExpressionNode rhs() {
        return rhs;
    }

    public void setRHS(ExpressionNode expr) {
        this.rhs = expr;
    }

    public Location location() {
        return lhs.location();
    }

    public void _dump(Dumper d) {
        d.printMember("lhs", lhs);
        d.printMember("rhs", rhs);
    }
}
