package io.vincent.learning.stack.concurrency.mq;

/**
 * Producer.
 *
 * @author Vincent.Lu.
 * @since 2023/5/27
 */
public class Producer {

    private final XQueue<Message> messageXQueue;

    int messageCount = 0;

    public Producer(XQueue<Message> messageXQueue) {
        this.messageXQueue = messageXQueue;
    }

    public void produce() throws InterruptedException {
        synchronized (messageXQueue) {
            if (messageXQueue.queue.size() == 10) {
                messageXQueue.wait();
                return;
            }
            int i = messageCount++;
            Message message = Message.builder().id((long) i).topic("topic" + i).body("body" + i).build();
            System.out.println("Produce: " + message);
            this.messageXQueue.put(message);
        }
    }

}