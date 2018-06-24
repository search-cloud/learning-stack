package io.vincent.learning.stack.aio

import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler
import java.util.*

/**
 *
 * @author Asion.
 * @since 2018/6/22.
 */
class ReadCompletionHandler(private val channel: AsynchronousSocketChannel) : CompletionHandler<Int, ByteBuffer> {
    override fun completed(result: Int?, attachment: ByteBuffer?) {
        attachment!!.flip()
        val body = ByteArray(attachment.remaining())
        attachment.get(body)
        val req = String(body, Charsets.UTF_8)
        println("The time server receive order: $req")
        val currentTime = if ("Query Time Order".equals(req, true)) {
            Date(System.currentTimeMillis()).toString()
        } else {
            "Bad Order"
        }
        // write byte to client
        doWrite(currentTime)
    }

    private fun doWrite(currentTime: String) {
        val bytes = currentTime.toByteArray()
        val writeBuffer = ByteBuffer.allocate(bytes.size)
        writeBuffer.put(bytes)
        writeBuffer.flip()
        channel.write(
                writeBuffer, writeBuffer,
                object : CompletionHandler<Int, ByteBuffer> {
                    override fun completed(result: Int, buffer: ByteBuffer) {
                        if (buffer.hasRemaining()) channel.write(buffer, buffer, this)
                    }
                    override fun failed(exc: Throwable?, attachment: ByteBuffer?) = channel.close()
                }
        )
    }

    override fun failed(exc: Throwable?, attachment: ByteBuffer?) = this.channel.close()

}
