package io.vincent.compiler.c.asm;

/**
 * label 标签
 */
public class Label extends Assembly {
	// 符号
	protected Symbol symbol;

	public Label() {
		this(new UnnamedSymbol());
	}

	public Label(Symbol sym) {
		this.symbol = sym;
	}

	public Symbol symbol() {
		return symbol;
	}

	public boolean isLabel() {
		return true;
	}

	public String toSource(SymbolTable table) {
		return symbol.toSource(table) + ":";
	}

	public String dump() {
		return "(Label " + symbol.dump() + ")";
	}
}
