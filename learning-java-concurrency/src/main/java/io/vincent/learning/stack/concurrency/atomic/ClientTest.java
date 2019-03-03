package io.vincent.learning.stack.concurrency.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Vincent on 2/25/19.
 *
 * @author Vincent
 * @since 1.0, 2/25/19
 */
@Slf4j
public class ClientTest {

    private static int clients = 5000;
    private static int threadPoolSize = 200;

    private Adder adder;

    public ClientTest(Adder adder) {
        this.adder = adder;
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
