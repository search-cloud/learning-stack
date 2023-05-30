package io.vincent.learning.stack.concurrency.interrupt;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * InterruptDemo.
 *
 * @author Vincent.Lu.
 * @since 2023/5/25
 */
public class InterruptDemo {
    public static void main(String[] args) {
        InterruptDemo interruptDemo = new InterruptDemo();
        interruptDemo.testSync();
        interruptDemo.testLock();
    }

    public void testSync() {
        Object lock = new Object();
        Thread t = new Thread(() -> {
            int k = 0;
            synchronized (lock) {
                for (int i = 0; i < 10000000; i++) {
                    k++;
                }
                System.out.println("Finished." + k);
            }
        });

        t.start();
        t.interrupt();
    }

    public void testLock() {
        Lock lock = new ReentrantLock();
        Thread t = new Thread(() -> {
            int k = 0;
            try {
                lock.lockInterruptibly();
                for (int i = 0; i < 10000000; i++) {
                    k++;
                }
                System.out.println("Finished." + k);
            } catch (InterruptedException e) {
                System.out.println("Interrupted." + k);
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        t.start();
        t.interrupt();
    }
}
