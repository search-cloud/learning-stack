package io.vincent.compiler.c.utils;

public final class AsmUtils {
    private AsmUtils() {}

    // #@@range/align{
    public static long align(long n, long alignment) {
        return (n + alignment - 1) / alignment * alignment;
    }
    // #@@}
}
