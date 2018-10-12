package io.asion.concurrent

import org.junit.Test

/**
 * @author Asion.
 * @since 2017/8/25.
 */
class YieldDemoTest {
    // 启动两个线程测试
    @Test
    fun testYield() {
        val r1 = YieldDemo("Thread-1")
        r1.start()

        val r2 = YieldDemo("Thread-2")
        r2.start()
    }
}