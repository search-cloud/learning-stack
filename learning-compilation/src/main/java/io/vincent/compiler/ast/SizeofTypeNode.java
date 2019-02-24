package io.vincent.compiler.ast;
import io.vincent.compiler.context.Dumper;
import io.vincent.compiler.type.*;

public class SizeofTypeNode extends ExpressionNode {
    protected TypeNode operand;
    protected TypeNode type;

    public SizeofTypeNode(TypeNode operand, TypeRef type) {
        this.operand = operand;
        this.type = new TypeNode(type);
    }

    public Type operand() {
        return operand.type();
    }

    public TypeNode operandTypeNode() {
        return operand;
    }

    public Type type() {
        return type.type();
    }

    public TypeNode typeNode() {
        return type;
    }

    public Location location() {
        return operand.location();
    }

    public void _dump(Dumper d) {
        d.printMember("operand", operand);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
