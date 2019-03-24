package io.vincent.compiler.c.ast;
import io.vincent.compiler.c.type.Type;

abstract public class LHSNode extends ExpressionNode {
    protected Type type, origType;

    public Type type() {
        return type != null ? type : origType();
    }

    public void setType(Type t) {
        this.type = t;
    }

    abstract protected Type origType();

    public long allocSize() { return origType().allocSize(); }

    public boolean isLvalue() { return true; }
    public boolean isAssignable() { return isLoadable(); }

    public boolean isLoadable() {
        Type t = origType();
        return !t.isArray() && !t.isFunction();
    }
}
