package io.vincent.learning.stack.concurrency.atomic;

import java.util.concurrent.atomic.LongAdder;

import io.vincent.learning.stack.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * LongAdder JDK1.8 才加入的.
 * Striped64
 * 使用分段思想, 针对 CAS 循环检查值的缺点.
 *
 * @author Vincent
 * @since 1.0, 2/24/19
 */
@Slf4j
@ThreadSafe
public class LongAdderDemo implements Adder {

    private static final LongAdder count = new LongAdder();

    public static void main(String[] args) {
        new AdderClientTest(new LongAdderDemo(), 50000, 200).runConcurrencyTest();
    }

    public void add() {
        count.increment();
    }

    @Override
    public String count() {
        return count.toString();
    }


}
