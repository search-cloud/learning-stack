package io.vincent.learning.stack.aio

import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler
import java.util.concurrent.CountDownLatch

/**
 * Client write message to Server callback handler.
 *
 * @author Asion.
 * @since 2018/6/22.
 */
class ClientWriteCompletionHandler(private val channel: AsynchronousSocketChannel, private val countDownLatch: CountDownLatch) : CompletionHandler<Void, AsyncTimeClientHandler> {
    override fun completed(result: Void?, attachment: AsyncTimeClientHandler?) {

        val req = "Query Time Order".toByteArray()
        val writeBuffer = ByteBuffer.allocate(req.size)
        writeBuffer.put(req)
        writeBuffer.flip()
        channel.write(writeBuffer, writeBuffer, object : CompletionHandler<Int, ByteBuffer> {
            override fun completed(result: Int?, attachment: ByteBuffer?) {
                if (attachment!!.hasRemaining()) {
                    channel.write(attachment, attachment, this)
                } else {
                    val readBuffer = ByteBuffer.allocate(1024)
                    channel.read(readBuffer, readBuffer, object : CompletionHandler<Int, ByteBuffer> {
                        override fun completed(result: Int?, attachment: ByteBuffer?) {
                            attachment!!.flip()
                            val bytes = ByteArray(attachment.remaining())
                            attachment.get(bytes)
                            val body = String(bytes, Charsets.UTF_8)
                            println("Server time is: $body")
                            countDownLatch.countDown()
                        }

                        override fun failed(exc: Throwable?, attachment: ByteBuffer?) {
                            channel.close()
                            countDownLatch.countDown()
                        }

                    })
                }
            }

            override fun failed(exc: Throwable?, attachment: ByteBuffer?) {
                channel.close()
                countDownLatch.countDown()
            }

        })

    }

    override fun failed(exc: Throwable?, attachment: AsyncTimeClientHandler?) {
        channel.close()
        countDownLatch.countDown()
    }

}
