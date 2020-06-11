package io.vincent.learning.stack.concurrency.locks;

import java.util.concurrent.locks.Lock;

import lombok.extern.slf4j.Slf4j;

/**
 * 自己实现锁重入例子。
 *
 * @author Vincent
 * @see LockSimpleImpl
 * @since 1.0, 2019/4/9
 */
@Slf4j
public class LockSimpleImplReentrantExample {

    private Lock lock = new LockSimpleImpl();

    private void a() {
        lock.lock();
        log.info("a method!");
        b();
        c("a");
        lock.unlock();
    }

    private void b() {
        lock.lock();
        log.info("b method!");
        c("b");
        lock.unlock();
    }

    private void c(String m) {
        lock.lock();
        log.info("{}.c method!", m);
        lock.unlock();
    }

    public static void main(String[] args) {
        LockSimpleImplReentrantExample reentrantExample = new LockSimpleImplReentrantExample();
        new Thread(reentrantExample::a).start();
    }
}
