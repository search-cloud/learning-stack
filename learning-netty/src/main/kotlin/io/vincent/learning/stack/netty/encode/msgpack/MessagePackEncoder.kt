package io.vincent.learning.stack.netty.encode.msgpack

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import org.msgpack.MessagePack

/**
 * Encoder.
 *
 * Created by Vincent on 2018/8/30.
 * @author Vincent
 * @since 1.0, 2018/8/30
 */
class MessagePackEncoder : MessageToByteEncoder<Any> () {

    /**
     * encode object to byte array.
     */
    override fun encode(ctx: ChannelHandlerContext?, msg: Any?, out: ByteBuf?) {
        try {

            val pack = MessagePack()
            // 将object类型的POJO对象转码成byte[]
            val bytes = pack.write(msg)

            /**
             * Java NIO API自带的缓冲区类功能相当有限，没有经过优化，使用JDK的ByteBuffer操作更复杂。
             * 故而Netty的作者Trustin Lee为了实现高效率的网络传输，重新造轮子，
             * Netty中的ByteBuf实际上就相当于JDK中的ByteBuffer，
             * 其作用是在Netty中通过Channel传输数据。
             */
            // 将这个字节数组写入到缓冲区
            out!!.writeBytes(bytes)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}