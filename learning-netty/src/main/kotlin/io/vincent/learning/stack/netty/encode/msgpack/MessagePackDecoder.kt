package io.vincent.learning.stack.netty.encode.msgpack

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageDecoder
import org.msgpack.MessagePack

/**
 * Decoder.
 *
 * Created by Vincent on 2018/8/30.
 * @author Vincent
 * @since 1.0, 2018/8/30
 */
class MessagePackDecoder : MessageToMessageDecoder<ByteBuf>() {

    /**
     * decode byte array to object.
     */
    override fun decode(ctx: ChannelHandlerContext?, msg: ByteBuf?, out: MutableList<Any>?) {

        // 获取需要解码数据的长度
        val length = msg!!.readableBytes()
        // 新创建一个字节数组，其长度设置为上面获取的长度
        val array = ByteArray(length)
        // 将要解码的数据填充到新创建的数组中
        msg.getBytes(msg.readerIndex(), array, 0, length)
        val pack = MessagePack()
        // 调用MessagePack的read方法将其反序列化为object对象
        // 将解码后的对象加入到解码对象列表
        out!!.add(pack.read(array))
    }
}