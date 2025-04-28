package io.vincent.learning.stack.algorithm.leetcode;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(valid).isTrue();
    }

    @Test
    public void isValid1() {
        boolean valid = No20ValidParenthesesJ.isValid("()[]{}");
        assertThat(valid).isTrue();
    }

    @Test
    public void isValid2() {
        boolean valid = No20ValidParenthesesJ.isValid("(]");
        assertThat(valid).isFalse();
    }

    @Test
    public void isValid3() {
        boolean valid = No20ValidParenthesesJ.isValid("([)]");
        assertThat(valid).isFalse();
    }

    @Test
    public void isValid4() {
        boolean valid = No20ValidParenthesesJ.isValid("{[]}");
        assertThat(valid).isTrue();
    }

    @Test
    public void isValid5() {
        boolean valid = No20ValidParenthesesJ.isValid("(]]");
        assertThat(valid).isFalse();
    }

    @Test
    public void isValid6() {
        boolean valid = No20ValidParenthesesJ.isValid("]");
        assertThat(valid).isFalse();
    }
}
