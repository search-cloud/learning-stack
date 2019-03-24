package io.vincent.compiler.c.ast;
import io.vincent.compiler.c.context.Dumper;
import io.vincent.compiler.c.type.Type;

public class BinaryOpNode extends ExpressionNode {
    private String operator;
    private ExpressionNode left, right;
    protected Type type;

    public BinaryOpNode(ExpressionNode left, String op, ExpressionNode right) {
        super();
        this.operator = op;
        this.left = left;
        this.right = right;
    }

    public BinaryOpNode(Type t, ExpressionNode left, String op, ExpressionNode right) {
        super();
        this.operator = op;
        this.left = left;
        this.right = right;
        this.type = t;
    }

    public String operator() {
        return operator;
    }

    public Type type() {
        return (type != null) ? type : left.type();
    }

    public void setType(Type type) {
        if (this.type != null)
            throw new Error("BinaryOp#setType called twice");
        this.type = type;
    }

    public ExpressionNode left() {
        return left;
    }

    public void setLeft(ExpressionNode left) {
        this.left = left;
    }

    public ExpressionNode right() {
        return right;
    }

    public void setRight(ExpressionNode right) {
        this.right = right;
    }

    public Location location() {
        return left.location();
    }

    public void _dump(Dumper d) {
        d.printMember("operator", operator);
        d.printMember("left", left);
        d.printMember("right", right);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
