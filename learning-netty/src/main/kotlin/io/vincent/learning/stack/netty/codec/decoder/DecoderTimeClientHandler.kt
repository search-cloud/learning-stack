package io.vincent.learning.stack.netty.codec.decoder

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerAdapter
import io.netty.channel.ChannelHandlerContext
import org.slf4j.LoggerFactory

/**
 *
 * @author Vincent.
 * @since 2018/8/6.
 */
class DecoderTimeClientHandler : ChannelHandlerAdapter() {

//    private var message: ByteBuf

    private var req: ByteArray = "Query Time Order${System.getProperty("line.separator")}".toByteArray()
    private var counter = 0

    init {
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
//        val byteBuf = msg as ByteBuf
//        req = ByteArray(byteBuf.readableBytes())
//        byteBuf.readBytes(req)
        val body = msg as String
        println("Server time is: $body; the counter is: ${++counter}")
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        logger.warn("Unexpected exception from downstream: ${cause!!.message}")
        ctx!!.close()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(DecoderTimeClientHandler::class.java)
    }
}