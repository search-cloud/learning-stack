package io.vincent.compiler.ast;

/**
 * 语句节点
 */
 public abstract class StatementNode implements Node {
    protected Location location;

    public StatementNode(Location loc) {
        this.location = loc;
    }

    public Location location() {
        return location;
    }

    abstract public <S,E> S accept(ASTVisitor<S,E> visitor);
}
