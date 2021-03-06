package io.vincent.learning.stack.netty.codec.encode.msgpack.server

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

/**
 * Created by Vincent on 2018/8/6.
 * @author Vincent
 * @since 1.0, 2018/8/6
 */
class MessagePackEchoServerHandler : ChannelInboundHandlerAdapter() {

    override fun channelRead(ctx: ChannelHandlerContext?, message: Any?) {
        println("Server receive the MessagePack message: [$message]")
        ctx!!.writeAndFlush(message)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace()
        ctx?.close()
    }

}
