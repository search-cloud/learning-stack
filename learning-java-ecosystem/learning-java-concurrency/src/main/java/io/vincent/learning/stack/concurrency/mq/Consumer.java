package io.vincent.learning.stack.concurrency.mq;

/**
 * Consumer.
 *
 * @author Vincent.Lu.
 * @since 2023/5/27
 */
public class Consumer {

    private final XQueue<Message> messageXQueue;

    public Consumer(XQueue<Message> messageXQueue) {
        this.messageXQueue = messageXQueue;
    }

    void consume() {
        synchronized (messageXQueue) {
            if (messageXQueue.queue.size() == 0) {
                return;
            }
            Message message = messageXQueue.take();
            if (message == null) return;
            System.out.println("Consume: " + message);
        }
    }
}
