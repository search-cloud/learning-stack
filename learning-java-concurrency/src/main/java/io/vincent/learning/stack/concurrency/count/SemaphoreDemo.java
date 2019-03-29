package io.vincent.learning.stack.concurrency.count;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Vincent on 2019/3/18.
 *
 * @author Vincent
 * @since 1.0, 2019/3/18
 */
@Slf4j
public class SemaphoreDemo {

    // 计数初始值
    private static int count = 0;
    // 线程数
    private static int t = 4;

    private static void count() {
        log.info("{}, {}", Thread.currentThread().getName(), count);
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(t);
        Semaphore[] semaphores = new Semaphore[t];
        for (int j = 0; j < t; j++) {
            semaphores[j] = new Semaphore(1);
            if (j != t - 1) {
                semaphores[j].acquire();
            }
        }

        for (int i = 0; i < t; i++) {
            final Semaphore lastSemaphore = i == 0 ? semaphores[t - 1] : semaphores[i - 1];
            final Semaphore curSemaphore = semaphores[i];

            executorService.execute(() -> {
                while (true) {
                    try {
                        lastSemaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count();
                    if (count > 100) {
                        System.exit(0);
                    }
                    curSemaphore.release();
                }
            });
        }

        executorService.shutdown();
    }
}
