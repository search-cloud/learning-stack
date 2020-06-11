package io.vincent.learning.stack.aio

import java.net.InetSocketAddress
import java.nio.channels.AsynchronousServerSocketChannel
import java.util.concurrent.CountDownLatch

/**
 *
 * @author Asion.
 * @since 2018/6/22.
 */
class AsyncTimeServerHandler(port: Int) : Runnable {

    var asynchronousServerSocketChannel: AsynchronousServerSocketChannel = AsynchronousServerSocketChannel.open()
    lateinit var countDownLatch: CountDownLatch

    init {
        asynchronousServerSocketChannel.bind(InetSocketAddress(port))
        println("The time server is start in port: $port")
    }

    override fun run() {
        countDownLatch = CountDownLatch(1)
        doAccept()
        countDownLatch.await()
    }

    private fun doAccept() {
        asynchronousServerSocketChannel.accept(this, AcceptCompletionHandler())
    }
}