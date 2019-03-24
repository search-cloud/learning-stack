package io.vincent.compiler.c.entity;

public interface EntityVisitor<T> {
    T visit(DefinedVariable var);
    T visit(UndefinedVariable var);
    T visit(DefinedFunction func);
    T visit(UndefinedFunction func);
    T visit(Constant c);
}
