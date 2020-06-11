package io.vincent.learning.stack.concurrency.happensbefore;

import lombok.extern.slf4j.Slf4j;

/**
 * Volatile 规则：
 * Volatile 修饰的共享变量，所有的写操作，Happens Before 于所有的读操作。
 * 也就是所有的写操作，对所有的读操作，【可见】。
 *
 * @author Vincent
 * @since 1.0, 2019/3/28
 */
@Slf4j
class VolatileExample {
    private int x = 0;
    private volatile boolean v = false;

    void write() {
        log.info("Write, {}, x = {}", Thread.currentThread().getName(), x);
        x = 42;
        v = true;
    }

    void read() {
        log.info("Read, {}, x = {}", Thread.currentThread().getName(), x);
        if (v) {
            // 这里 x 会是多少呢？
            log.info("{}, x = {}", Thread.currentThread().getName(), x);
        }
    }

    public static void main(String[] args) {
        VolatileExample ve = new VolatileExample();

//        new Thread(ve::read).start();
        new Thread(ve::write).start();
        new Thread(ve::read).start();
    }
}
