package io.vincent.learning.stack.netty.protocol.netty.codec

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.vincent.learning.stack.netty.protocol.netty.Header
import io.vincent.learning.stack.netty.protocol.netty.MessageType
import io.vincent.learning.stack.netty.protocol.netty.NettyMessage
import org.junit.Test
import java.util.*

/**
 * Created by Vincent on 9/4/18.
 *
 * @author Vincent
 * @since 1.0, 9/4/18
 */
class MarshallingCodecTest {

    private var marshallingEncoder: MarshallingEncoder = MarshallingEncoder()
    private var marshallingDecoder: MarshallingDecoder = MarshallingDecoder()

    fun getMessage(): NettyMessage {
        val header = Header(length = 123, sessionId = System.currentTimeMillis(), type = MessageType.SERVICE_RESP.value, priority = 7)
        val attachment = HashMap<String, Any>()
        for (i in 0..9) {
            attachment["ciyt --> $i"] = "lilinfeng $i"
        }
        header.attachment = attachment
        return NettyMessage(header, "abcdefg-----------------------AAAAAA")
    }

    fun encode(msg: NettyMessage): ByteBuf {
        val sendBuf = Unpooled.buffer()
        sendBuf.writeInt(msg.header.crcCode)
        sendBuf.writeInt(msg.header.length)
        sendBuf.writeLong(msg.header.sessionId)
        sendBuf.writeByte(msg.header.type.toInt())
        sendBuf.writeByte(msg.header.priority.toInt())

        sendBuf.writeInt(msg.header.attachment!!.size)
        var key: String?
        var keyArray: ByteArray?
        var value: Any?

        for (param in msg.header.attachment!!.entries) {
            key = param.key
            keyArray = key.toByteArray(charset("UTF-8"))
            sendBuf.writeInt(keyArray.size)
            sendBuf.writeBytes(keyArray)
            value = param.value
            marshallingEncoder.encode(value, sendBuf)
        }

        if (msg.body != null) {
            marshallingEncoder.encode(msg.body!!, sendBuf)
        } else
            sendBuf.writeInt(0)
        sendBuf.setInt(4, sendBuf.readableBytes())
        return sendBuf
    }

    fun decode(byteBuf: ByteBuf): NettyMessage {
        val header = Header(byteBuf.readInt(), byteBuf.readInt(), byteBuf.readLong(), byteBuf.readByte(), byteBuf.readByte())

        val size = byteBuf.readInt()
        if (size > 0) {
            val attch = HashMap<String, Any>(size)
            var keySize: Int
            var keyArray: ByteArray?
            var key: String?
            for (i in 0 until size) {
                keySize = byteBuf.readInt()
                keyArray = ByteArray(keySize)
                byteBuf.readBytes(keyArray)
                key = String(keyArray, Charsets.UTF_8)
                attch[key] = marshallingDecoder.decode(byteBuf)
            }
            header.attachment = attch
        }

        val message = NettyMessage(header, null)

        if (byteBuf.readableBytes() > 4) {
            message.body = (marshallingDecoder.decode(byteBuf))
        }
        return message
    }

    /**
     * @param args
     * @throws Exception
     */
    @Test
    fun testMarshall() {
        val test = MarshallingCodecTest()
        val message = test.getMessage()
        println("$message [body ] " + message.body)

        for (i in 0..4) {
            val buf = test.encode(message)
            val decodeMsg = test.decode(buf)
            println("$decodeMsg [body ] " + decodeMsg.body)
            println("-------------------------------------------------")

        }

    }
}
