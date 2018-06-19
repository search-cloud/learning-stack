package io.vincent.learning.stack.netty.io.pool

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 *
 * @author Asion.
 * @since 2018/6/16.
 */
class TimeServerHandlerExecutor(maxPoolSize: Int, queueSize: Int) {

    private var executor: ThreadPoolExecutor = ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2, maxPoolSize, 120L, TimeUnit.SECONDS,
            ArrayBlockingQueue<Runnable>(queueSize))

    fun execute(task: Runnable) {
        executor.execute(task)
    }

}