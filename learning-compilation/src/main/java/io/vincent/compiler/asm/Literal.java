package io.vincent.compiler.asm;

public interface Literal extends Comparable<Literal> {
    String toSource();
    String toSource(SymbolTable table);
    String dump();
    void collectStatistics(Statistics stats);
    boolean isZero();
    Literal plus(long diff);
    int cmp(IntegerLiteral i);
    int cmp(NamedSymbol sym);
    int cmp(UnnamedSymbol sym);
    int cmp(SuffixedSymbol sym);
}
