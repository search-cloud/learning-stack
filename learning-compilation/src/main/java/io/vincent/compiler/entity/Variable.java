package io.vincent.compiler.entity;

import io.vincent.compiler.ast.TypeNode;

public abstract class Variable extends Entity {
	Variable(boolean priv, TypeNode type, String name) {
		super(priv, type, name);
	}
}
