package io.vincent.learning.stack.concurrency.happensbefore;

import lombok.extern.slf4j.Slf4j;

// 以下代码来源于【参考 1】
@Slf4j
class VolatileExample {
    private int x = 0;
    private volatile boolean v = false;

    public void write() {
        x = 42;
        v = true;
    }

    public void read() {
        if (v) {
            // 这里 x 会是多少呢？
            log.info("Thread: {}, {}", Thread.currentThread().getName(), x);
        }
    }
}
