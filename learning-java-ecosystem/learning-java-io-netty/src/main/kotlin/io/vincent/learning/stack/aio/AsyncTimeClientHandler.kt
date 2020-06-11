package io.vincent.learning.stack.aio

import java.net.InetSocketAddress
import java.nio.channels.AsynchronousSocketChannel
import java.util.concurrent.CountDownLatch

/**
 *
 * @author Asion.
 * @since 2018/6/22.
 */
class AsyncTimeClientHandler(private val host: String, private val port: Int) : Runnable {

    var asynClientChannel: AsynchronousSocketChannel = AsynchronousSocketChannel.open()
    lateinit var countDownLatch: CountDownLatch

    init {
        println("The time client is connected to server in host: $host, port: $port")
    }

    override fun run() {
        countDownLatch = CountDownLatch(1)
        asynClientChannel.connect<AsyncTimeClientHandler>(InetSocketAddress(host, port), this, ClientWriteCompletionHandler(asynClientChannel, countDownLatch))
        countDownLatch.await()
        asynClientChannel.close()
    }
}