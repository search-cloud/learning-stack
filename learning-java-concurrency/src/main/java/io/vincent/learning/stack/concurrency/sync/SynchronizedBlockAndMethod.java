package io.vincent.learning.stack.concurrency.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Vincent on 3/4/19.
 *
 * @author Vincent
 * @since 1.0, 3/4/19
 */
@Slf4j
public class SynchronizedBlockAndMethod {

    /**
     * 修饰 this 当前对象.
     *
     * @param t 标志
     */
    public void test1(int t) {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                log.info("test1 {} - {}", t, i);
            }
        }
    }

    /**
     * 修饰 非静态方法.
     *
     * @param t 标志
     */
    public synchronized void test2(int t) {
        for (int i = 0; i < 10; i++) {
            log.info("test2 {} - {}", t, i);
        }
    }

    public static void main(String[] args) {
        final SynchronizedBlockAndMethod staticBlock1 = new SynchronizedBlockAndMethod();
        final SynchronizedBlockAndMethod staticBlock2 = new SynchronizedBlockAndMethod();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> staticBlock1.test1(1));
        executorService.execute(() -> staticBlock2.test1(2));

        executorService.execute(() -> staticBlock1.test2(1));
        executorService.execute(() -> staticBlock2.test2(2));
    }
}
