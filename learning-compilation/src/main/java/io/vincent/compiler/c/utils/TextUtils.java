package io.vincent.compiler.c.utils;

import io.vincent.compiler.c.parser.Parser;

import java.io.UnsupportedEncodingException;

abstract public class TextUtils {
    private static final byte vtab = 11;

    static public String dumpString(String str) {
        try {
            return dumpString(str, Parser.SOURCE_ENCODING);
        }
        catch (UnsupportedEncodingException ex) {
            throw new Error("UTF-8 is not supported??: " + ex.getMessage());
        }
    }

    static public String dumpString(String string, String encoding)
            throws UnsupportedEncodingException {
        byte[] src = string.getBytes(encoding);
        StringBuilder buf = new StringBuilder();
        buf.append("\"");
        for (byte aSrc : src) {
            int c = toUnsigned(aSrc);
            if (c == '"') buf.append("\\\"");
            else if (isPrintable(c)) buf.append((char) c);
            else if (c == '\b') buf.append("\\b");
            else if (c == '\t') buf.append("\\t");
            else if (c == '\n') buf.append("\\n");
            else if (c == vtab) buf.append("\\v");
            else if (c == '\f') buf.append("\\f");
            else if (c == '\r') buf.append("\\r");
            else {
                buf.append("\\").append(Integer.toOctalString(c));
            }
        }
        buf.append("\"");
        return buf.toString();
    }

    static private int toUnsigned(byte b) {
        return b >= 0 ? b : 256 + b;
    }

    static public boolean isPrintable(int c) {
        return (' ' <= c) && (c <= '~');
    }
}
