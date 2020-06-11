package io.vincent.learning.stack.concurrency.producerconsumer

/**
 * Producer monitor.
 *
 * @author Vincent
 * @since 1.0, 12/24/18
 */
class MessageProducer(private val messageQueue: MessageQueue) : Runnable {

    init {
        Thread(this, "Producer").start()
    }

    override fun run() {
        for (i in 0 until 10) {
            messageQueue.produce(Message(Thread.currentThread().id + i, "Message-Head-$i", "Message-Body-$i"))
        }
    }

}
