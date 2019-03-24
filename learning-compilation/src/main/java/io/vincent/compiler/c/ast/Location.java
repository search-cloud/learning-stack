package io.vincent.compiler.c.ast;
import io.vincent.compiler.c.parser.Token;

/**
 * 所在源码文件的位置信息.
 */
public class Location {
    // 源文件名称
    protected String sourceName;
    // token
    protected CFlatToken token;

    public Location(String sourceName, Token token) {
        this(sourceName, new CFlatToken(token));
    }

    public Location(String sourceName, CFlatToken token) {
        this.sourceName = sourceName;
        this.token = token;
    }

    public String sourceName() {
        return sourceName;
    }

    public CFlatToken token() {
        return token;
    }

    /** line number */
    public int lineno() {
        return token.lineno();
    }

    public int column() {
        return token.column();
    }

    public String line() {
        return token.includedLine();
    }

    public String numberedLine() {
        return "line " + token.lineno() + ": " + line();
    }

    public String toString() {
        return sourceName + ":" + token.lineno();
    }
}
