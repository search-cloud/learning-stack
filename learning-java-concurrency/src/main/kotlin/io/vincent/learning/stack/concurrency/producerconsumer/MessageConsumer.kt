package io.vincent.learning.stack.concurrency.producerconsumer

/**
 * Consumer monitor.
 *
 * @author Vincent
 * @since 1.0, 12/24/18
 */
class MessageConsumer(private val messageQueue: MessageQueue) : Runnable {

    init {
        Thread(this, "Consumer").start()
    }

    override fun run() {
        for (i in 0 until 10) {
            messageQueue.consume()
        }
    }

}
