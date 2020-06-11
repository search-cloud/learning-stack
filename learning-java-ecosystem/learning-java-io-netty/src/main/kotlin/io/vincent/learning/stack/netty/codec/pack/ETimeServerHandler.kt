package io.vincent.learning.stack.netty.codec.pack

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import java.util.*

/**
 *
 * @author Vincent.
 * @since 2018/08/03.
 */
class ETimeServerHandler : ChannelInboundHandlerAdapter() {

    private var counter: Int = 0

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        val byteBuf = msg as ByteBuf
        val req = ByteArray(byteBuf.readableBytes())
        byteBuf.readBytes(req)
        // 使用substing模拟拆包
        val separator = System.getProperty("line.separator")
        val body = String(req, Charsets.UTF_8)
                .substring(0, req.size - separator.length)

        println("The time server receive order: $body; the counter is: ${++counter}")

        val currentTime = when {
            "Query Time Order".equals(body, true) -> "${Date(System.currentTimeMillis())}$separator"
            else -> "Bad Order$separator"
        }
        val resp = Unpooled.copiedBuffer(currentTime.toByteArray())
        ctx!!.writeAndFlush(resp)
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {
        ctx!!.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        println(cause!!.message)
        ctx!!.close()
    }

}