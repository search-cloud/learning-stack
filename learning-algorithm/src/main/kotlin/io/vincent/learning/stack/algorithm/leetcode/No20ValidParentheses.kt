package io.vincent.learning.stack.algorithm.leetcode

/**
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 *
 * An input string is valid if:
 *
 * Open brackets must be closed by the same type of brackets.
 * Open brackets must be closed in the correct order.
 * Note that an empty string is also considered valid.
 *
 * Example 1:
 *
 * Input: "()"
 * Output: true
 * Example 2:
 *
 * Input: "()[]{}"
 * Output: true
 * Example 3:
 *
 * Input: "(]"
 * Output: false
 * Example 4:
 *
 * Input: "([)]"
 * Output: false
 * Example 5:
 *
 * Input: "{[]}"
 * Output: true
 *
 * @author Vincent
 * @since 1.0, 2019/3/25
 */
object No20ValidParentheses {

    fun isValid(s: String): Boolean {
        val brackets = mapOf(')' to '(', ']' to '[', '}' to '{')
        val stack = mutableListOf<Char>()

        s.forEach {
            when {
                brackets.containsValue(it) -> stack.add(it)
                brackets.containsKey(it) -> {
                    val b = stack.isEmpty() || (stack.last() != brackets[it])
                    when {
                        stack.isNotEmpty() -> stack.removeAt(stack.lastIndex)
                    }
                    if (b) return false
                }
            }
        }
        return stack.isEmpty()
    }
}
