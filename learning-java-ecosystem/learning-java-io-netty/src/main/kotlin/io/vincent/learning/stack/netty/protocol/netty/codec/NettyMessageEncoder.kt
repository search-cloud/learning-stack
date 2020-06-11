package io.vincent.learning.stack.netty.protocol.netty.codec

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import io.vincent.learning.stack.netty.protocol.netty.NettyMessage

/**
 * Created by Vincent on 9/4/18.
 * @author Vincent
 * @since 1.0, 9/4/18
 */
class NettyMessageEncoder: MessageToByteEncoder<NettyMessage>() {

    private val marshallingEncoder: MarshallingEncoder = MarshallingEncoder()

    override fun encode(ctx: ChannelHandlerContext, msg: NettyMessage?, sendBuf: ByteBuf) {

        if (msg?.header == null)
            throw Exception("The encode message is null")

        sendBuf.writeInt(msg.header.crcCode)
        sendBuf.writeInt(msg.header.length)
        sendBuf.writeLong(msg.header.sessionId)
        sendBuf.writeByte(msg.header.type.toInt())
        sendBuf.writeByte(msg.header.priority.toInt())


        // 处理 attachment
        if (msg.header.attachment != null) {
            sendBuf.writeInt(msg.header.attachment!!.size)

            var key: String?
            var keyArray: ByteArray?
            var value: Any?
            for (attachment in msg.header.attachment!!.entries) {
                key = attachment.key
                keyArray = key.toByteArray(charset("UTF-8"))
                sendBuf.writeInt(keyArray.size)
                sendBuf.writeBytes(keyArray)
                value = attachment.value
                marshallingEncoder.encode(value, sendBuf)
            }
        } else {
            // 关键点：没有Header扩展，要标志attach位，长度为 0
            sendBuf.writeInt(0)
        }

        // 处理 body
        if (msg.body != null) {
            marshallingEncoder.encode(msg.body!!, sendBuf)
        } else {
            // 关键点：没有Body，要标志body位，长度为0
            sendBuf.writeInt(0)
        }
        sendBuf.setInt(4, sendBuf.readableBytes() - 8)
    }
}