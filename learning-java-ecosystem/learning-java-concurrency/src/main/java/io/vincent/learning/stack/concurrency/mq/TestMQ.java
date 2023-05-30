package io.vincent.learning.stack.concurrency.mq;

/**
 * TestMQ.
 *
 * @author Vincent.Lu.
 * @since 2023/5/27
 */
public class TestMQ {
    public static void main(String[] args) {
        XQueue<Message> xQueue = new XQueue<>();
        Producer producer = new Producer(xQueue);

        for (int i = 0; i < 16; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        producer.produce();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        Consumer consumer = new Consumer(xQueue);
        new Thread(() -> {
            while (true) {
                consumer.consume();
            }
        }).start();
    }

}
