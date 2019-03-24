package io.vincent.compiler.c.entity;

import io.vincent.compiler.c.exception.SemanticException;

import java.util.ArrayList;
import java.util.List;

/**
 * 作用域
 */
abstract public class Scope {
    protected List<LocalScope> children;

    public Scope() {
        children = new ArrayList<LocalScope>();
    }

    public abstract boolean isTopLevel();
    public abstract TopLevelScope topLevel();
    public abstract Scope parent();

    protected void addChild(LocalScope s) {
        children.add(s);
    }

    abstract public Entity get(String name) throws SemanticException;
}
