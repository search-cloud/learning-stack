package io.vincent.compiler.c.ast;

import io.vincent.compiler.c.context.Dumper;
import io.vincent.compiler.c.exception.SemanticError;
import io.vincent.compiler.c.type.FunctionType;
import io.vincent.compiler.c.type.Type;

import java.util.List;

public class FuncallNode extends ExpressionNode {
    protected ExpressionNode expr;
    protected List<ExpressionNode> args;

    public FuncallNode(ExpressionNode expr, List<ExpressionNode> args) {
        this.expr = expr;
        this.args = args;
    }

    public ExpressionNode expr() {
        return expr;
    }

    /**
     * Returns a type of return value of the function which is refered
     * by expression.  This method expects expression.type().isCallable() is true.
     */
    public Type type() {
        try {
            return functionType().returnType();
        }
        catch (ClassCastException err) {
            throw new SemanticError(err.getMessage());
        }
    }

    /**
     * Returns a type of function which is refered by expression.
     * This method expects expression.type().isCallable() is true.
     */
    public FunctionType functionType() {
        return expr.type().getPointerType().baseType().getFunctionType();
    }

    public long numArgs() {
        return args.size();
    }

    public List<ExpressionNode> args() {
        return args;
    }

    // called from TypeChecker
    public void replaceArgs(List<ExpressionNode> args) {
        this.args = args;
    }

    public Location location() {
        return expr.location();
    }

    public void _dump(Dumper d) {
        d.printMember("expression", expr);
        d.printNodeList("args", args);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
