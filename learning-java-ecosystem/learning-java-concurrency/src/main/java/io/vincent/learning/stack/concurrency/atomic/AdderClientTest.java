package io.vincent.learning.stack.concurrency.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Vincent on 2/25/19.
 *
 * @author Vincent
 * @since 1.0, 2/25/19
 */
@Slf4j
public class AdderClientTest {

    private int clients = 5000;
    private int threadPoolSize = 200;

    private final Adder adder;

    public AdderClientTest(Adder adder, int clients, int threadPoolSize) {
        this.adder = adder;
        this.clients = clients;
        this.threadPoolSize = threadPoolSize;
    }

    void runConcurrencyTest() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadPoolSize);
        final CountDownLatch countDownLatch = new CountDownLatch(clients);
        for (int i = 0; i < clients; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    adder.add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    log.error("count error: ", e);
                }
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
            log.info("count: {}", adder.count());
        } catch (InterruptedException e) {
            log.error("count error: ", e);
        }
        executorService.shutdown();
    }

}
