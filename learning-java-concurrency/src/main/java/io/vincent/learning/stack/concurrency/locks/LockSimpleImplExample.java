package io.vincent.learning.stack.concurrency.locks;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Vincent on 2019/4/9.
 *
 * @author Vincent
 * @since 1.0, 2019/4/9
 */
@Slf4j
public class LockSimpleImplExample {

    private int i;
    private LockSimpleImpl lock = new LockSimpleImpl();

    public int increaseAndGet() {
        lock.lock();
        i++;
        lock.unlock();
        return i;
    }

    public int get() {
        return i;
    }

    public static void main(String[] args) {
        LockSimpleImplExample lockSimpleImplExample = new LockSimpleImplExample();
        ExecutorService service = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5000);

        for (int i = 0; i < 5000; i++) {
            service.execute(() -> {
                log.info("counts: {}", lockSimpleImplExample.increaseAndGet());
                latch.countDown();
            });
        }
        try {
            latch.await();
//            assertEquals("thread not safe: ", 5000, lockSimpleImplExample.get());
            assert 5000 == lockSimpleImplExample.get();
            log.info("sum: {}", lockSimpleImplExample.get());
        } catch (InterruptedException e) {
            log.error("", e);
        }
        service.shutdown();
    }

}
