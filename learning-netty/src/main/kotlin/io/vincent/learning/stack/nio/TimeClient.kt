package io.vincent.learning.stack.nio

import java.util.concurrent.Executors

/**
 *
 * @author Asion.
 * @since 2018/6/19.
 */
object TimeClient {
    @JvmStatic
    fun main(args: Array<String>) {
        var port = 8080
        if (args.isNotEmpty()) {
            try {
                port = Integer.valueOf(args[0])
            } catch (e: NumberFormatException) {
                //
            }

        }
        val threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2)
//        Thread(TimeClientHandler("127.0.0.1", port), "TimeClient-001").start()
        for (i in 0..100) threadPool.execute(
                TimeClientHandler("127.0.0.1", port, "TimeClient-00$i")
        )

        threadPool.shutdown()
    }


}