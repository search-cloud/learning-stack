package io.vincent.learning.stack.netty.codec.decoder

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerAdapter
import io.netty.channel.ChannelHandlerContext
import java.util.*

/**
 *
 * @author Vincent.
 * @since 2018/08/06.
 */
class DecoderTimeServerHandler : ChannelHandlerAdapter() {

    private var counter: Int = 0

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
//        val byteBuf = msg as ByteBuf
//        val req = ByteArray(byteBuf.readableBytes())
//        byteBuf.readBytes(req)
        // 使用substing模拟拆包
        val separator = System.getProperty("line.separator")
        val body = msg as String

        counter++
        println("The time server receive order: $body; the counter is: $counter")

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