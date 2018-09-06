package io.vincent.learning.stack.netty.protocol.netty.codec

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import io.vincent.learning.stack.netty.protocol.netty.Header
import io.vincent.learning.stack.netty.protocol.netty.NettyMessage
import java.util.*

/**
 * Created by Vincent on 9/4/18.
 * @author Vincent
 * @since 1.0, 9/4/18
 */
class NettyMessageDecoder(maxFrameLength: Int, lengthFieldOffset: Int, lengthFieldLength: Int) :
        LengthFieldBasedFrameDecoder(maxFrameLength, lengthFieldOffset, lengthFieldLength) {

    private val marshallingDecoder: MarshallingDecoder = MarshallingDecoder()

    override fun decode(ctx: ChannelHandlerContext, byteBuf: ByteBuf): Any? {
        val frame = super.decode(ctx, byteBuf) as ByteBuf? ?: return null

        val message = NettyMessage()
        val header = Header()
        header.crcCode = frame.readInt()
        header.length = frame.readInt()
        header.sessionId = frame.readLong()
        header.type = frame.readByte()
        header.priority = frame.readByte()

        val size = frame.readInt()
        if (size > 0) {
            val attachment = HashMap<String, Any>(size)
            var keySize: Int
            var keyArray: ByteArray?
            var key: String?
            for (i in 0 until size) {
                keySize = frame.readInt()
                keyArray = ByteArray(keySize)
                frame.readBytes(keyArray)
                key = String(keyArray, Charsets.UTF_8)
                attachment[key] = marshallingDecoder.decode(frame)
            }
            header.attachment = attachment
        }
        if (frame.readableBytes() > 4) {
            message.body = marshallingDecoder.decode(frame)
        }
        message.header = header
        return message
    }
}