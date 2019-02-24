package io.vincent.compiler.asm;

/**
 * 符号抽象
 */
public interface Symbol extends Literal {
	/**
	 * 符号名称
	 * @return 符号名称
	 */
	String name();

	/**
	 * 必须重新 toString 方法
	 * @return string
	 */
	String toString();

}
