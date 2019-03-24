package io.vincent.compiler.c.ast;
import io.vincent.compiler.c.context.Dumper;
import io.vincent.compiler.c.type.ArrayType;
import io.vincent.compiler.c.type.Type;

public class ArefNode extends LHSNode {
    private ExpressionNode expr, index;

    public ArefNode(ExpressionNode expr, ExpressionNode index) {
        this.expr = expr;
        this.index = index;
    }

    public ExpressionNode expr() { return expr; }
    public ExpressionNode index() { return index; }

    // isMultiDimension a[x][y][z] = true.
    // isMultiDimension a[x][y] = true.
    // isMultiDimension a[x] = false.
    public boolean isMultiDimension() {
        return (expr instanceof ArefNode) && !expr.origType().isPointer();
    }

    // Returns base expression of (multi-dimension) array.
    // e.g.  baseExpr of a[x][y][z] is a.
    public ExpressionNode baseExpr() {
        return isMultiDimension() ? ((ArefNode)expr).baseExpr() : expr;
    }

    // element size of this (multi-dimension) array
    public long elementSize() {
        return origType().allocSize();
    }

    public long length() {
        return ((ArrayType)expr.origType()).length();
    }

    protected Type origType() {
        return expr.origType().baseType();
    }

    public Location location() {
        return expr.location();
    }

    public void _dump(Dumper d) {
        if (type != null) {
            d.printMember("type", type);
        }
        d.printMember("expression", expr);
        d.printMember("index", index);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
