package io.vincent.learning.stack.netty.decoder

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerAdapter
import io.netty.channel.ChannelHandlerContext


/**
 * Created by Vincent on 2018/8/6.
 * @author Vincent
 * @since 1.0, 2018/8/6
 */
class EchoClientHandler : ChannelHandlerAdapter() {
    var counter: Int = 0

    override fun channelActive(ctx: ChannelHandlerContext?) {
        for (i in 0..9) {
            ctx!!.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.toByteArray()))
        }
    }

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        println("This is ${++counter} times receive server: [$msg]")
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {
        ctx?.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace()
        ctx?.close()
    }

    companion object {
        private const val ECHO_REQ: String = "Hi, Vincent.Lu. Welcome to Netty.${Delimiter.delimiter}"
    }
}