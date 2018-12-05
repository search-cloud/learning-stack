package io.vincent.learning.stack.concurrency.operations

import org.junit.Test

/**
 * @author Asion.
 * @since 2017/8/25.
 */
class JoinDemoTest {

    // 启动两个线程测试
    @Test
    @Throws(Exception::class)
    fun join() {
        val r1 = JoinDemo("Thread-1")
        val thread = Thread(r1, "Thread-1")
        r1.start(thread)

        val r2 = JoinDemo("Thread-2")
        r2.start()

        r1.join()
        r2.join()

        for (i in 10 downTo 1) {
            println("Thread: main" + Thread.currentThread().id + ", " + i)
        }

        println("Thread main" + Thread.currentThread().id + " finish.")
    }

}