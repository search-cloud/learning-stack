/* Generated By:JavaCC: Do not edit this line. ParserConstants.java */
package io.vincent.compiler.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ParserConstants {

	/**
	 * End of File.
	 */
	int EOF = 0;
	/**
	 * RegularExpression Id.
	 */
	int SPACES = 1;
	/**
	 * RegularExpression Id.
	 */
	int BLOCK_COMMENT = 4;
	/**
	 * RegularExpression Id.
	 */
	int LINE_COMMENT = 5;
	/**
	 * RegularExpression Id.
	 */
	int VOID = 6;
	/**
	 * RegularExpression Id.
	 */
	int CHAR = 7;
	/**
	 * RegularExpression Id.
	 */
	int SHORT = 8;
	/**
	 * RegularExpression Id.
	 */
	int INT = 9;
	/**
	 * RegularExpression Id.
	 */
	int LONG = 10;
	/**
	 * RegularExpression Id.
	 */
	int STRUCT = 11;
	/**
	 * RegularExpression Id.
	 */
	int UNION = 12;
	/**
	 * RegularExpression Id.
	 */
	int ENUM = 13;
	/**
	 * RegularExpression Id.
	 */
	int STATIC = 14;
	/**
	 * RegularExpression Id.
	 */
	int EXTERN = 15;
	/**
	 * RegularExpression Id.
	 */
	int CONST = 16;
	/**
	 * RegularExpression Id.
	 */
	int SIGNED = 17;
	/**
	 * RegularExpression Id.
	 */
	int UNSIGNED = 18;
	/**
	 * RegularExpression Id.
	 */
	int IF = 19;
	/**
	 * RegularExpression Id.
	 */
	int ELSE = 20;
	/**
	 * RegularExpression Id.
	 */
	int SWITCH = 21;
	/**
	 * RegularExpression Id.
	 */
	int CASE = 22;
	/**
	 * RegularExpression Id.
	 */
	int DEFAULT_ = 23;
	/**
	 * RegularExpression Id.
	 */
	int WHILE = 24;
	/**
	 * RegularExpression Id.
	 */
	int DO = 25;
	/**
	 * RegularExpression Id.
	 */
	int FOR = 26;
	/**
	 * RegularExpression Id.
	 */
	int RETURN = 27;
	/**
	 * RegularExpression Id.
	 */
	int BREAK = 28;
	/**
	 * RegularExpression Id.
	 */
	int CONTINUE = 29;
	/**
	 * RegularExpression Id.
	 */
	int GOTO = 30;
	/**
	 * RegularExpression Id.
	 */
	int TYPEDEF = 31;
	/**
	 * RegularExpression Id.
	 */
	int IMPORT = 32;
	/**
	 * RegularExpression Id.
	 */
	int SIZEOF = 33;
	/**
	 * RegularExpression Id.
	 */
	int IDENTIFIER = 34;
	/**
	 * RegularExpression Id.
	 */
	int INTEGER = 35;
	/**
	 * RegularExpression Id.
	 */
	int CHARACTER = 40;
	/**
	 * RegularExpression Id.
	 */
	int STRING = 45;

	/**
	 * Lexical state.
	 */
	int DEFAULT = 0;
	/**
	 * Lexical state.
	 */
	int IN_BLOCK_COMMENT = 1;
	/**
	 * Lexical state.
	 */
	int IN_CHARACTER = 2;
	/**
	 * Lexical state.
	 */
	int CHARACTER_TERM = 3;
	/**
	 * Lexical state.
	 */
	int IN_STRING = 4;

	/**
	 * Literal token values.
	 */
	String[] tokenImage = {
			"<EOF>",
			"<SPACES>",
			"\"/*\"",
			"<token of kind 3>",
			"\"*/\"",
			"<LINE_COMMENT>",
			"\"void\"",
			"\"char\"",
			"\"short\"",
			"\"int\"",
			"\"long\"",
			"\"struct\"",
			"\"union\"",
			"\"enum\"",
			"\"static\"",
			"\"extern\"",
			"\"const\"",
			"\"signed\"",
			"\"unsigned\"",
			"\"if\"",
			"\"else\"",
			"\"switch\"",
			"\"case\"",
			"\"default\"",
			"\"while\"",
			"\"do\"",
			"\"for\"",
			"\"return\"",
			"\"break\"",
			"\"continue\"",
			"\"goto\"",
			"\"typedef\"",
			"\"import\"",
			"\"sizeof\"",
			"<IDENTIFIER>",
			"<INTEGER>",
			"\"\\\'\"",
			"<token of kind 37>",
			"<token of kind 38>",
			"<token of kind 39>",
			"\"\\\'\"",
			"\"\\\"\"",
			"<token of kind 42>",
			"<token of kind 43>",
			"<token of kind 44>",
			"\"\\\"\"",
			"\"(\"",
			"\".\"",
			"\";\"",
			"\"=\"",
			"\",\"",
			"\")\"",
			"\"...\"",
			"\"{\"",
			"\"}\"",
			"\"[\"",
			"\"]\"",
			"\"*\"",
			"\":\"",
			"\"+=\"",
			"\"-=\"",
			"\"*=\"",
			"\"/=\"",
			"\"%=\"",
			"\"&=\"",
			"\"|=\"",
			"\"^=\"",
			"\"<<=\"",
			"\">>=\"",
			"\"?\"",
			"\"||\"",
			"\"&&\"",
			"\">\"",
			"\"<\"",
			"\">=\"",
			"\"<=\"",
			"\"==\"",
			"\"!=\"",
			"\"|\"",
			"\"^\"",
			"\"&\"",
			"\">>\"",
			"\"<<\"",
			"\"+\"",
			"\"-\"",
			"\"/\"",
			"\"%\"",
			"\"++\"",
			"\"--\"",
			"\"!\"",
			"\"~\"",
			"\"->\"",
	};

}
