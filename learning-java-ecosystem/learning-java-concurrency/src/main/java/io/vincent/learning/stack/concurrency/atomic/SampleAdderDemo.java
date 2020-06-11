package io.vincent.learning.stack.concurrency.atomic;

import io.vincent.learning.stack.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * 普通的 count.
 *
 * @author Vincent
 * @since 1.0, 2/24/19
 */
@Slf4j
@NotThreadSafe
public class SampleAdderDemo implements Adder {

    private static int count = 0;

    public static void main(String[] args) {
        new ClientTest(new SampleAdderDemo()).runConcurrencyTest();
    }

    @Override
    public void add() {
        count++;
    }

    @Override
    public String count() {
        return String.valueOf(count);
    }

}
