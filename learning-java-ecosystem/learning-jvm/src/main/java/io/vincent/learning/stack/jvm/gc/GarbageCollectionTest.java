package io.vincent.learning.stack.jvm.gc;

import io.vincent.learning.stack.jvm.Customer;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Vincent on 3/1/19.
 *
 * @author Vincent
 * @since 1.0, 3/1/19
 */
@Slf4j
public class GarbageCollectionTest {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        long freeMemory = runtime.freeMemory();

        log.info("freeMemory: {}k", freeMemory / 1024);

        for (int i = 0; i < 1000; i++) {
            Customer customer = new Customer("name" + i, i);
        }

        freeMemory = runtime.freeMemory();
        log.info("freeMemory: {}k", freeMemory / 1024);

        System.gc();

        freeMemory = runtime.freeMemory();
        log.info("freeMemory: {}k", freeMemory / 1024);
    }
}
