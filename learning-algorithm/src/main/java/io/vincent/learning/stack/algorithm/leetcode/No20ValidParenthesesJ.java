package io.vincent.learning.stack.algorithm.leetcode;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Vincent on 2019/3/25.
 *
 * @author Vincent
 * @since 1.0, 2019/3/25
 */
public class No20ValidParenthesesJ {

    private static Map<Character, Character> brackets = new HashMap<>(6);

    static {
        brackets.put(')', '(');
        brackets.put(']', '[');
        brackets.put('}', '{');
    }

    public static boolean isValid(@NotNull String s) {
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char value = s.charAt(i);
            if (brackets.containsValue(value)) {
                stack.push(value);
            } else if (brackets.containsKey(value) && (stack.isEmpty() || !stack.pop().equals(brackets.get(value)))) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
