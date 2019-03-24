package io.vincent.compiler.c.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public abstract class ListUtils {
    public static <T> List<T> reverse(List<T> list) {
        List<T> result = new ArrayList<>(list.size());
        ListIterator<T> it = list.listIterator(list.size());
        while (it.hasPrevious()) {
            result.add(it.previous());
        }
        return result;
    }
}
