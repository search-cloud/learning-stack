package io.vincent.learning.stack.netty.aio

/**
 *
 * @author Asion.
 * @since 2018/6/22.
 */
object TimeClient {
    @JvmStatic
    fun main(args: Array<String>) {
        val port = 8080
        val timeClientHandler = AsyncTimeClientHandler("127.0.0.1", port)
        Thread(timeClientHandler, "AIO-AsyncTimeClientHandler-001").start()
    }
}