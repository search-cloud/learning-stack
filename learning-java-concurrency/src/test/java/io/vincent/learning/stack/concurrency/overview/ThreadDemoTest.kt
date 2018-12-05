package io.vincent.learning.stack.concurrency.overview

import org.junit.Test

/**
 * @author Asion.
 * @since 2017/8/25.
 */
class ThreadDemoTest {

    @Test
    @Throws(Exception::class)
    fun start() {
        val t1 = ThreadDemo("Thread-1")
        t1.start()

        val t2 = ThreadDemo("Thread-2")
        t2.start()
    }

}