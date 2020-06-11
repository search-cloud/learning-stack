package io.vincent.learning.stack.javacore;

import org.junit.Test;

/**
 * 字符串学习.
 *
 * @author Vincent
 * @since 1.0, 2/28/19
 */
public class StringTests {
    @Test
    public void testString() {

        // a b 都在字符串常量池中
        String a = "Hello";
        String b = "Hello";
        // c is not in the String pools
        String c = new String("Hello");

        System.out.println(a == b);
        System.out.println(a == c);

        int i = 76;
        String is = "76";

        // toString 方法生成的字符串也不再常量池中.
        System.out.println(Integer.toString(i) == is);

        // intern 可以将字符串放入产量池中, 注意: 如果已经存在的情况.
        System.out.println(Integer.toString(i).intern() == is);
    }
}
