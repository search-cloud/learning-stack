package io.vincent.compiler.c.ast;

public interface DeclarationVisitor<T> {
    T visit(StructNode struct);
    T visit(UnionNode union);
    T visit(TypedefNode typedef);
}
