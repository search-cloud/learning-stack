package io.vincent.learning.stack.concurrency.atomic;

import io.vincent.learning.stack.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.LongAdder;

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

    private static LongAdder count = new LongAdder();

    public static void main(String[] args) {
        new ClientTest(new LongAdderDemo()).runConcurrencyTest();
    }

    public void add() {
        count.increment();
    }

    @Override
    public String count() {
        return count.toString();
    }


}
