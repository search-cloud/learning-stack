package io.vincent.learning.stack.concurrency.limiter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Vincent on 2019/4/6.
 *
 * @author Vincent
 * @since 1.0, 2019/4/6
 */
@Slf4j
public class GuavaLimiter {

    private static long limit = 1000;

    private LoadingCache<Long, LongAdder> counter =
            CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.SECONDS)
                    .build(new CacheLoader<Long, LongAdder>() {
                        @Override
                        public LongAdder load(@NotNull Long seconds) {
                            return new LongAdder();
                        }
                    });


    public void exec() throws ExecutionException, InterruptedException {

        while (!Thread.currentThread().isInterrupted()) {
            // 得到当前秒
            long currentSeconds = System.currentTimeMillis() / 1000;
            LongAdder longAdder = counter.get(currentSeconds);
            longAdder.increment();
            if (longAdder.sum() > limit) {
                log.warn("限流了: {}", currentSeconds);
                continue;
            }
            // 业务处理
            log.info("业务处理中…… 当前线程: {}", Thread.currentThread().getName());
            Thread.sleep(500);
        }
    }

    // 线程数
    private static int t = 5000;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(t);
        GuavaLimiter guavaLimiter = new GuavaLimiter();
        for (int i = 0; i < t; i++) {

            executorService.execute(() -> {
                try {
                    guavaLimiter.exec();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
    }
}
