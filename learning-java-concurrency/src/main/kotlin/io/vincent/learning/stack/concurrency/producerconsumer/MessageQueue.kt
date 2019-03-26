package io.vincent.learning.stack.concurrency.producerconsumer

import java.util.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * Thread's communication Message Queue.
 *
 * @author Vincent
 * @since 1.0, 12/24/18
 */
class MessageQueue {

    private val queue = LinkedList<Message>()
    private val pCounter = AtomicInteger()
    private val cCounter = AtomicInteger()
    private val capacity = 5

    @Synchronized
    internal fun produce(message: Message) {
        // Producer thread waits while list is full
        while (queue.size == capacity) {
            println("Message Queue is full: $capacity, waiting for consume!")
            wait()
        }

        // To insert the jobs in the list
        queue.add(message)
        println("Producer produced-${pCounter.getAndIncrement()} $message")

        // Notifies the consumer thread that now it can start consuming
        notifyAll()

        // Makes the working of program easier to  understand
        Thread.sleep(1000)
    }

    @Synchronized
    internal fun consume(): Message? {

        // Consumer thread waits while list is empty
        while (queue.isEmpty()) {
            println("Message Queue is empty, waiting for produce!")
            wait()
        }

        // To retrieve the first element in the list
        val message = queue.poll()
        println("Consumer consumed-${cCounter.getAndIncrement()} $message")

        // Wake up producer thread
        notifyAll()

        // And sleep
        Thread.sleep(1000)

        return message
    }

    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    private fun Any.wait() = (this as java.lang.Object).wait()

    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    private fun Any.notify() = (this as java.lang.Object).notify()

    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    private fun Any.notifyAll() = (this as java.lang.Object).notifyAll()
}
