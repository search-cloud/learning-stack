package io.vincent.learning.stack.concurrency.overview

import kotlin.test.Test

/**
 * @author Asion.
 * @since 2017/8/25.
 */
class RunnableDemoTest {


    @Test
    @Throws(Exception::class)
    fun testStart() {
        // 启动两个线程测试
        val r1 = RunnableDemo("Thread-1")
        r1.start()

        val r2 = RunnableDemo("Thread-2")
        r2.start()
    }

}