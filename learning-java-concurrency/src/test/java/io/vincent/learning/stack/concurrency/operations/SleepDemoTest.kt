package io.vincent.learning.stack.concurrency.operations

import org.junit.Test

/**
 * @author Asion.
 * @since 2017/8/25.
 */
class SleepDemoTest {
    // 启动两个线程测试
    @Test
    fun testRunnableSleep() {
        val r1 = SleepDemo("Thread-1")
        r1.start()

        val r2 = SleepDemo("Thread-2")
        r2.start()
    }
}