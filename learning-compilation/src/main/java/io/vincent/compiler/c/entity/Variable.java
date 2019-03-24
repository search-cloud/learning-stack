package io.vincent.compiler.c.entity;

import io.vincent.compiler.c.ast.TypeNode;

public abstract class Variable extends Entity {
	Variable(boolean priv, TypeNode type, String name) {
		super(priv, type, name);
	}
}
