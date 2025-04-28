package io.vincent.learning.stack.concurrency.operations

import kotlin.test.Test

/**
 * @author Asion.
 * @since 2017/8/25.
 */
class PriorityDemoTest {
    /**
     * 启动两个线程测试 Priority
     */
    @Test
    fun testPriority() {
        val r1 = PriorityDemo("Thread-1")
        val thread = Thread(r1, "Thread-1")
        thread.priority = Thread.MIN_PRIORITY
        r1.start(thread)

        val r2 = PriorityDemo("Thread-2")
        val thread1 = Thread(r1, "Thread-2")
        println("priority: $thread1.priority")
        r2.start()
    }
}