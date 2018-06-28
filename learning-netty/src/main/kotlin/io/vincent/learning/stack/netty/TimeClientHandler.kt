package io.vincent.learning.stack.netty

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerAdapter
import io.netty.channel.ChannelHandlerContext
import org.slf4j.LoggerFactory

/**
 *
 * @author Asion.
 * @since 2018/6/24.
 */
class TimeClientHandler : ChannelHandlerAdapter() {

    private var firstMessage: ByteBuf

    init {
        val req = "Query Time Order".toByteArray()
        firstMessage = Unpooled.buffer(req.size)
        firstMessage.writeBytes(req)
    }

    override fun channelActive(ctx: ChannelHandlerContext?) {
        ctx!!.writeAndFlush(firstMessage)
    }

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        val byteBuf = msg as ByteBuf
        val req = ByteArray(byteBuf.readableBytes())
        byteBuf.readBytes(req)
        val body = String(req, Charsets.UTF_8)
        println("Server time is: $body")
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        logger.warn("Unexpected exception from downstream: ${cause!!.message}")
        ctx!!.close()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(TimeClientHandler::class.java)
    }
}