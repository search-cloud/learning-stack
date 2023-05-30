package io.vincent.learning.stack.concurrency.mutex;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Mutex.
 *
 * @author Vincent.Lu.
 * @since 2023/5/25
 */
public class Mutex {

    private final Sync sync = new Sync();

    static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            return compareAndSetState(0, 1);
        }

        @Override
        protected boolean tryRelease(int arg) {
            return compareAndSetState(1, 0);
        }
    }

    public void lock() {
        sync.tryAcquire(0);
    }

    public void unlock() {
        sync.tryRelease(1);
    }

}
