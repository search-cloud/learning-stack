package io.vincent.compiler.ast;
import io.vincent.compiler.context.Dumper;
import io.vincent.compiler.type.*;

public class IntegerLiteralNode extends LiteralNode {
    protected long value;

    public IntegerLiteralNode(Location loc, TypeRef ref, long value) {
        super(loc, ref);
        this.value = value;
    }

    public long value() {
        return value;
    }

    public void _dump(Dumper d) {
        d.printMember("typeNode", typeNode);
        d.printMember("value", value);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
