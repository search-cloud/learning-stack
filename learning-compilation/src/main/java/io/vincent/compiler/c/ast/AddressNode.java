package io.vincent.compiler.c.ast;
import io.vincent.compiler.c.context.Dumper;
import io.vincent.compiler.c.type.Type;

public class AddressNode extends ExpressionNode {
    final ExpressionNode expr;
    Type type;

    public AddressNode(ExpressionNode expr) {
        this.expr = expr;
    }

    public ExpressionNode expr() {
        return expr;
    }

    public Type type() {
        if (type == null) throw new Error("type is null");
        return type;
    }

    /** Decides type of this node.
     * This method is called from DereferenceChecker. */
    public void setType(Type type) {
        if (this.type != null) throw new Error("type set twice");
        this.type = type;
    }

    public Location location() {
        return expr.location();
    }

    public void _dump(Dumper d) {
        if (type != null) {
            d.printMember("type", type);
        }
        d.printMember("expression", expr);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
