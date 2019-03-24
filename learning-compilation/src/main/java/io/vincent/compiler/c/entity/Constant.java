package io.vincent.compiler.c.entity;

import io.vincent.compiler.c.ast.ExpressionNode;
import io.vincent.compiler.c.ast.TypeNode;
import io.vincent.compiler.c.context.Dumper;

public class Constant extends Entity {
    private TypeNode type;
    private String name;
    private ExpressionNode value;

    public Constant(TypeNode type, String name, ExpressionNode value) {
        super(true, type, name);
        this.value = value;
    }

    public boolean isAssignable() { return false; }
    public boolean isDefined() { return true; }
    public boolean isInitialized() { return true; }
    public boolean isConstant() { return true; }

    public ExpressionNode value() { return value; }

    public void _dump(Dumper d) {
        d.printMember("name", name);
        d.printMember("typeNode", typeNode);
        d.printMember("value", value);
    }

    public <T> T accept(EntityVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
