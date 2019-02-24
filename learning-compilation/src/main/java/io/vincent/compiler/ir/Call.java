package io.vincent.compiler.ir;

import io.vincent.compiler.asm.Type;
import io.vincent.compiler.entity.Entity;
import io.vincent.compiler.entity.Function;

import java.util.List;

/**
 * 函数调用表达式
 */
public class Call extends Expression {
    // 表达式
    private Expression expression;
    // 参数列表
    private List<Expression> args;

    public Call(Type type, Expression expression, List<Expression> args) {
        super(type);
        this.expression = expression;
        this.args = args;
    }

    public Expression expr() { return expression; }
    public List<Expression> args() { return args; }

    public long numArgs() {
        return args.size();
    }

    /** Returns true if this funcall is NOT a function pointer call. */
    public boolean isStaticCall() {
        return (expression.getEntityForce() instanceof Function);
    }

    /**
     * Returns a function object which is refered by expression.
     * This method expects this is static function call (isStaticCall()).
     */
    public Function function() {
        Entity ent = expression.getEntityForce();
        if (ent == null) {
            throw new Error("not a static funcall");
        }
        return (Function)ent;
    }

    public <S,E> E accept(IRVisitor<S,E> visitor) {
        return visitor.visit(this);
    }

    public void _dump(Dumper d) {
        d.printMember("expression", expression);
        d.printMembers("args", args);
    }
}
