package io.vincent.compiler.c.ast;

import io.vincent.compiler.c.context.Dumper;
import io.vincent.compiler.c.type.TypeRef;
import io.vincent.compiler.c.entity.ConstantEntry;

public class StringLiteralNode extends LiteralNode {
    protected String value;
    protected ConstantEntry entry;

    public StringLiteralNode(Location loc, TypeRef ref, String value) {
        super(loc, ref);
        this.value = value;
    }

    public String value() {
        return value;
    }

    public ConstantEntry entry() {
        return entry;
    }

    public void setEntry(ConstantEntry ent) {
        entry = ent;
    }

    public void _dump(Dumper d) {
        d.printMember("value", value);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
