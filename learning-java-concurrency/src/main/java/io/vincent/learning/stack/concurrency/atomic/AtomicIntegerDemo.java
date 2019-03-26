package io.vincent.learning.stack.concurrency.atomic;

import io.vincent.learning.stack.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Atomic integer use CAS
 *  compare and swap. 比较然后交换值.
 *    do {} while()
 *
 *  如何做比较呢?
 *
 *
 * @author Vincent
 * @since 1.0, 2/24/19
 */
@Slf4j
@ThreadSafe
public class AtomicIntegerDemo implements Adder {

    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        new ClientTest(new AtomicIntegerDemo()).runConcurrencyTest();
    }


    @Override
    public void add() {
        count.getAndIncrement();
    }

    @Override
    public String count() {
        return String.valueOf(count.get());
    }
}
