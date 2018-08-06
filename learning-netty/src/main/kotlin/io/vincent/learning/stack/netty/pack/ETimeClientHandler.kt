package io.vincent.learning.stack.netty.pack

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
class ETimeClientHandler : ChannelHandlerAdapter() {

//    private var message: ByteBuf

    private var req: ByteArray
    private var counter = 0

    init {
        req = "Query Time Order".toByteArray()
//        message = Unpooled.buffer(req.size)
//        message.writeBytes(req)
    }

    override fun channelActive(ctx: ChannelHandlerContext?) {
        var message: ByteBuf

        for (i in 0..100) {
            message = Unpooled.buffer(req.size)
            message.writeBytes(req)
            ctx!!.writeAndFlush(message)
        }

    }

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        val byteBuf = msg as ByteBuf
        req = ByteArray(byteBuf.readableBytes())
        byteBuf.readBytes(req)
        val body = String(req, Charsets.UTF_8)
        println("Server time is: $body; the counter is: ${++counter}")
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        logger.warn("Unexpected exception from downstream: ${cause!!.message}")
        ctx!!.close()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ETimeClientHandler::class.java)
    }
}