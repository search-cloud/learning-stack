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
public class SynchronizedStaticMethod {

    /**
     * synchronized 修饰类
     *
     * @param t 标志
     */
    public static void test1(int t) {
        synchronized (SynchronizedStaticMethod.class) {
            for (int i = 0; i < 10; i++) {
                log.info("test1 {} - {}", t, i);
            }
        }
    }

    /**
     * synchronized 修饰静态方法
     *
     * @param t 标志
     */
    public synchronized static void test2(int t) {
        for (int i = 0; i < 10; i++) {
            log.info("test2 {} - {}", t, i);
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> test1(1));
        executorService.execute(() -> test1(2));

        executorService.execute(() -> test2(1));
        executorService.execute(() -> test2(2));
    }
}
