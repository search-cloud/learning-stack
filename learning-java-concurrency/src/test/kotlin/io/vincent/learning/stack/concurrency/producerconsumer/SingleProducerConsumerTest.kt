package io.vincent.learning.stack.concurrency.producerconsumer

/**
 * Test.
 *
 * @author Vincent
 * @since 1.0, 12/24/18
 */
object SingleProducerConsumerTest {

    @JvmStatic
    fun main(args: Array<String>) {
        val m = MessageQueue()
        // 2ed producer.
        MessageProducer(m)
        MessageProducer(m)
        // 4th consumer.
        MessageConsumer(m)
        MessageConsumer(m)
        MessageConsumer(m)
        MessageConsumer(m)
    }
}
