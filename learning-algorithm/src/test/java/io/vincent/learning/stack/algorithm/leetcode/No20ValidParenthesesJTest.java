package io.vincent.learning.stack.algorithm.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 2019/3/25.
 *
 * @author Vincent
 * @since 1.0, 2019/3/25
 */
public class No20ValidParenthesesJTest {

    @Test
    public void isValid() {
        boolean valid = No20ValidParenthesesJ.isValid("()");
        assertTrue(valid);
    }

    @Test
    public void isValid1() {
        boolean valid = No20ValidParenthesesJ.isValid("()[]{}");
        assertTrue(valid);
    }

    @Test
    public void isValid2() {
        boolean valid = No20ValidParenthesesJ.isValid("(]");
        assertFalse(valid);
    }

    @Test
    public void isValid3() {
        boolean valid = No20ValidParenthesesJ.isValid("([)]");
        assertFalse(valid);
    }

    @Test
    public void isValid4() {
        boolean valid = No20ValidParenthesesJ.isValid("{[]}");
        assertTrue(valid);
    }

    @Test
    public void isValid5() {
        boolean valid = No20ValidParenthesesJ.isValid("(]]");
        assertFalse(valid);
    }

    @Test
    public void isValid6() {
        boolean valid = No20ValidParenthesesJ.isValid("]");
        assertFalse(valid);
    }
}
