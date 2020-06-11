package io.vincent.learning.stack.concurrency.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.jetbrains.annotations.NotNull;

/**
 * 实现一个简单的锁🔐
 *
 * @author Vincent
 * @since 1.0, 2019/4/7
 */
public class LockSimpleImpl implements Lock {

    // 是否已加锁
    private volatile boolean locked = false;
    // 持有当前锁的线程
    private Thread lockHolder = null;
    // 锁的数量
    private int lockedCount = 0;

    @Override
    public synchronized void lock() {
        Thread current = Thread.currentThread();
        while (locked && current != lockHolder) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        locked = true;
        lockHolder = current;
        lockedCount++;

    }

    @Override
    public synchronized void unlock() {
        if (lockHolder == Thread.currentThread()) {
            lockedCount--;
            if (lockedCount == 0) {
                locked = false;
                notify();
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, @NotNull TimeUnit unit) throws InterruptedException {
        return false;
    }

    @NotNull
    @Override
    public Condition newCondition() {
        return null;
    }
}
