package io.vincent.learning.stack.netty.aio

/**
 *
 * @author Asion.
 * @since 2018/6/22.
 */
object TimeServer {
    @JvmStatic
    fun main(args: Array<String>) {
        val port = 8080
        val timeServerHandler = AsyncTimeServerHandler(port)
        Thread(timeServerHandler, "AIO-AsyncTimeServerHandler-001").start()
    }
}