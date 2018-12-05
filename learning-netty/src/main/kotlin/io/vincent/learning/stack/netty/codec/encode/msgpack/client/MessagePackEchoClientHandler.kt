package io.vincent.learning.stack.netty.codec.encode.msgpack.client

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.vincent.learning.stack.netty.codec.encode.Message
import java.util.*


/**
 * Created by Vincent on 2018/8/6.
 * @author Vincent
 * @since 1.0, 2018/8/6
 */
class MessagePackEchoClientHandler(private val sendNumber: Int) : ChannelInboundHandlerAdapter() {

    override fun channelActive(ctx: ChannelHandlerContext?) {
        val messages = buildMessages()

        for (m in messages) {
            ctx!!.write(m)
        }
        ctx!!.flush()
    }

    private fun buildMessages(): ArrayList<Message> {
        val messages = arrayListOf<Message>()
        for (i in 0..sendNumber) {
            messages.add(Message(i.toLong(), "ABCDEF --> $i", i, Date(), i.toDouble()))
        }
        return messages
    }

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        println("Client receive the MessagePack message: [$msg]")
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext?) {
        ctx?.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause?.printStackTrace()
        ctx?.close()
    }
}
