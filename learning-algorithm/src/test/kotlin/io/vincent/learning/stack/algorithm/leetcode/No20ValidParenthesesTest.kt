package io.vincent.learning.stack.algorithm.leetcode

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Created by Vincent on 2019/3/25.
 *
 * @author Vincent
 * @since 1.0, 2019/3/25
 */
class No20ValidParenthesesTest {

    @Test
    fun isValid() {
        val valid = No20ValidParentheses.isValid("()")
        assertTrue(valid)
    }

    @Test
    fun isValid1() {
        val valid = No20ValidParentheses.isValid("()[]{}")
        assertTrue(valid)
    }

    @Test
    fun isValid2() {
        val valid = No20ValidParentheses.isValid("(]")
        assertFalse(valid)
    }

    @Test
    fun isValid3() {
        val valid = No20ValidParentheses.isValid("([)]")
        assertFalse(valid)
    }

    @Test
    fun isValid4() {
        val valid = No20ValidParentheses.isValid("{[]}")
        assertTrue(valid)
    }

    @Test
    fun isValid5() {
        val valid = No20ValidParentheses.isValid("(]]")
        assertFalse(valid)
    }

    @Test
    fun isValid6() {
        val valid = No20ValidParentheses.isValid("]")
        assertFalse(valid)
    }
}
