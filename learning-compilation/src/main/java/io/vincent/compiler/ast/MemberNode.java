package io.vincent.compiler.ast;

import io.vincent.compiler.context.Dumper;
import io.vincent.compiler.exception.SemanticError;
import io.vincent.compiler.type.CompositeType;
import io.vincent.compiler.type.Type;

public class MemberNode extends LHSNode {
    private ExpressionNode expr;
    private String member;

    public MemberNode(ExpressionNode expr, String member) {
        this.expr = expr;
        this.member = member;
    }

    public CompositeType baseType() {
        try {
            return expr.type().getCompositeType();
        }
        catch (ClassCastException err) {
            throw new SemanticError(err.getMessage());
        }
    }

    public ExpressionNode expr() {
        return expr;
    }

    public String member() {
        return member;
    }

    public long offset() {
        return baseType().memberOffset(member);
    }

    protected Type origType() {
        return baseType().memberType(member);
    }

    public Location location() {
        return expr.location();
    }

    public void _dump(Dumper d) {
        if (type != null) {
            d.printMember("type", type);
        }
        d.printMember("expression", expr);
        d.printMember("member", member);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
