package io.vincent.learning.stack.aio

import java.nio.ByteBuffer
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.CompletionHandler

/**
 * 接收到client的回调处理。
 *
 * @author Asion.
 * @since 2018/6/22.
 */
class AcceptCompletionHandler : CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {
    override fun completed(result: AsynchronousSocketChannel, attachment: AsyncTimeServerHandler) {
        attachment.asynchronousServerSocketChannel.accept(attachment, this)
        val buffer = ByteBuffer.allocate(1024)
        result.read(buffer, buffer, ServerReadCompletionHandler(result))
    }

    override fun failed(exc: Throwable, attachment: AsyncTimeServerHandler) {
        exc.printStackTrace()
        attachment.countDownLatch.countDown()
    }

}
