package io.vincent.learning.stack.netty.codec.decoder

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

/**
 * Created by Vincent on 2018/8/6.
 * @author Vincent
 * @since 1.0, 2018/8/6
 */
class EchoServerHandler : ChannelInboundHandlerAdapter() {

    var counter: Int = 0

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        val body = msg as String
        println("This is ${++counter} times receive client: [$body]")

        val echo = Unpooled.copiedBuffer("$body${Delimiter.delimiter}".toByteArray())
        ctx?.writeAndFlush(echo)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace()
        ctx?.close()
    }

}
