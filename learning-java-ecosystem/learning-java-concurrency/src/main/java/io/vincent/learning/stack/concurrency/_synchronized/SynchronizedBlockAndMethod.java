package io.vincent.learning.stack.concurrency._synchronized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 * synchronized(this) 代码块，锁定当前对象
 * synchronized 修饰普通非静态方法，锁定操作该方法的对象。
 *
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
            doSomething(t, "test1 {} - {}");
        }
    }

    private void doSomething(int t, String s) {
        for (int i = 0; i < 10; i++) {
            log.info(s, t, i);
        }
    }

    /**
     * 修饰 非静态方法.
     *
     * @param t 标志
     */
    public synchronized void test2(int t) {
        doSomething(t, "test2 {} - {}");
    }

    public static void main(String[] args) {
        final SynchronizedBlockAndMethod staticBlock1 = new SynchronizedBlockAndMethod();
        final SynchronizedBlockAndMethod staticBlock2 = new SynchronizedBlockAndMethod();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> staticBlock1.test1(1));
        executorService.execute(() -> staticBlock2.test1(2));

        executorService.execute(() -> staticBlock1.test2(1));
        executorService.execute(() -> staticBlock2.test2(2));
        executorService.shutdown();
    }
}
