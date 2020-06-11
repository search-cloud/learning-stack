package io.vincent.learning.stack.netty

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import java.util.*

/**
 *
 * @author Asion.
 * @since 2018/6/24.
 */
class TimeServerHandler : ChannelInboundHandlerAdapter() {

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        val byteBuf = msg as ByteBuf
        val req = ByteArray(byteBuf.readableBytes())
        byteBuf.readBytes(req)
        val body = String(req, Charsets.UTF_8)
        println("The time server receive order: $body")
        val currentTime = when {
            "Query Time Order".equals(body, true) -> Date(System.currentTimeMillis()).toString()
            else -> "Bad Order"
        }
        val resp = Unpooled.copiedBuffer(currentTime.toByteArray())
        ctx!!.write(resp)
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {
        ctx!!.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        ctx!!.close()
    }

}