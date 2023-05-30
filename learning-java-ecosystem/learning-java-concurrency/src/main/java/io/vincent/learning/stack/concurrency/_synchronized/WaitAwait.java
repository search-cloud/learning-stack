package io.vincent.learning.stack.concurrency._synchronized;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * WaitAwait.
 *
 * @author Vincent.Lu.
 * @since 2023/5/25
 */
public class WaitAwait {
    public static void main(String[] args) {
        WaitAwait waitAwait = new WaitAwait();
        waitAwait.testSyncWait();
        waitAwait.testConditionWait();

    }

    // 测试 synchronized wait
    public void testSyncWait() {
        Object obj = new Object();
        Thread t1 = new Thread(() -> {
            System.out.println("before synchronized wait1...");
            // wait
            try {
                synchronized (obj) {
                    obj.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("after synchronized wait1...");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("before synchronized wait2...");
            // wait
            try {
                synchronized (obj) {
                    obj.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("after synchronized wait2...");
        });

        t1.start();
        t2.start();

        // 线程里使用了wait 如果，没有唤醒就永远wait了
        // 主线程先拿到了 lock, 先唤醒，才到wait，所以，主线程休眠很短一段时间，先让子线程拿到 lock
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (obj) {
//            obj.notify();
//            obj.notify();
            obj.notifyAll();
        }
    }

    // 测试 condition wait
    public void testConditionWait() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread t1 = new Thread(() -> {
            System.out.println("before condition wait1...");
            // wait
            try {
                lock.lock();
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println("after condition wait1...");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("before condition wait2...");
            // wait
            try {
               lock.lock();
               condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println("after condition wait2...");
        });

        t1.start();
        t2.start();

        // 线程里 await 如果，没有唤醒就永远await了
        // 主线程先拿到了 lock, 先唤醒，才到 await，所以，主线程休眠很短一段时间，先让子线程拿到 lock
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.lock();
        condition.signalAll();
        lock.unlock();
    }
}
