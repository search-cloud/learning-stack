package io.vincent.learning.stack.jvm.attach;

import java.util.concurrent.TimeUnit;

/**
 * TraceTest.
 *
 * @author Vincent.Lu.
 * @since 2023/4/24
 */
public class TraceEmptyTest {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            System.out.println(foo());
            TimeUnit.SECONDS.sleep(3);
        }
    }

    public static int foo() {
        return 100;
    }

}
