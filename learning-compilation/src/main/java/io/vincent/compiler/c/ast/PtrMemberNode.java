package io.vincent.compiler.c.ast;

import io.vincent.compiler.c.context.Dumper;
import io.vincent.compiler.c.exception.SemanticError;
import io.vincent.compiler.c.type.CompositeType;
import io.vincent.compiler.c.type.PointerType;
import io.vincent.compiler.c.type.Type;

public class PtrMemberNode extends LHSNode {
    public ExpressionNode expr;
    public String member;

    public PtrMemberNode(ExpressionNode expr, String member) {
        this.expr = expr;
        this.member = member;
    }

    public CompositeType dereferedCompositeType() {
        try {
            PointerType pt = expr.type().getPointerType();
            return pt.baseType().getCompositeType();
        }
        catch (ClassCastException err) {
            throw new SemanticError(err.getMessage());
        }
    }

    public Type dereferedType() {
        try {
            PointerType pt = expr.type().getPointerType();
            return pt.baseType();
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
        return dereferedCompositeType().memberOffset(member);
    }

    protected Type origType() {
        return dereferedCompositeType().memberType(member);
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
