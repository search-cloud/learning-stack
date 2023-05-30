package io.vincent.learning.stack.concurrency.mq;

import java.util.LinkedList;

/**
 * XQueue.
 *
 * @author Vincent.Lu.
 * @since 2023/5/27
 */
public class XQueue<T> {

    private static final int MAX_SIZE = 10;
    final LinkedList<T> queue = new LinkedList<>();

    public T take() {
        synchronized (queue) {
            if (queue.size() == 0) {
                return null;
            }
            T remove = queue.remove();
            System.out.println("queue size: " + queue.size());
            return remove;
        }
    }

    public void put(T m) throws InterruptedException {
        synchronized (queue) {
            // 默认队列满了，丢弃消息
            if (queue.size() == MAX_SIZE) {
                return;
            }
            Thread.sleep((long) (Math.random() * 1000));
            this.queue.add(m);
            System.out.println("queue size: " + queue.size());
        }
    }
}
