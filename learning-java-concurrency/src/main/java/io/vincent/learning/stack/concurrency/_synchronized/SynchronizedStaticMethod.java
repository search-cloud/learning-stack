package io.vincent.learning.stack.concurrency._synchronized;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * synchronized (XXX.class) 锁定所有该类的对象。
 * synchronized 修饰静态方法，也是锁定所有该类的实例对象。
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
            doSomething(t, "test1 {} - {}");
        }
    }

    private static void doSomething(int t, String s) {
        for (int i = 0; i < 10; i++) {
            log.info(s, t, i);
        }
    }

    /**
     * synchronized 修饰静态方法
     *
     * @param t 标志
     */
    public synchronized static void test2(int t) {
        doSomething(t, "test2 {} - {}");
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> test1(1));
        executorService.execute(() -> test1(2));

        executorService.execute(() -> test2(1));
        executorService.execute(() -> test2(2));
        test1(0);
        executorService.shutdown();
    }
}
