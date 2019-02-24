package io.vincent.compiler.type;

import io.vincent.compiler.exception.*;

/**
 * 抽象的类型.
 */
public interface Type {

	long sizeUnknown = -1;

	long size();

	boolean isSameType(Type other);

	default long allocSize() {
		return size();
	}

	default long alignment() {
		return allocSize();
	}

	default boolean isVoid() {
		return false;
	}

	default boolean isInt() {
		return false;
	}

	default boolean isInteger() {
		return false;
	}

	default boolean isSigned() {
		throw new Error("#isSigned for non-integer type");
	}

	default boolean isPointer() {
		return false;
	}

	default boolean isArray() {
		return false;
	}

	default boolean isCompositeType() {
		return false;
	}

	default boolean isStruct() {
		return false;
	}

	default boolean isUnion() {
		return false;
	}

	default boolean isUserType() {
		return false;
	}

	default boolean isFunction() {
		return false;
	}

	// Ability methods (unary)
	default boolean isAllocatedArray() {
		return false;
	}

	default boolean isIncompleteArray() {
		return false;
	}

	default boolean isScalar() {
		return false;
	}

	default boolean isCallable() {
		return false;
	}

	// Ability methods (binary)
	boolean isCompatible(Type other);

	boolean isCastableTo(Type target);

	default Type baseType() {
		throw new SemanticError("#baseType called for undereferable type");
	}

	// Cast methods
	default IntegerType getIntegerType() {
		return (IntegerType) this;
	}

	default PointerType getPointerType() {
		return (PointerType) this;
	}

	default FunctionType getFunctionType() {
		return (FunctionType) this;
	}

	default StructType getStructType() {
		return (StructType) this;
	}

	default UnionType getUnionType() {
		return (UnionType) this;
	}

	default CompositeType getCompositeType() {
		return (CompositeType) this;
	}

	default ArrayType getArrayType() {
		return (ArrayType) this;
	}
}
