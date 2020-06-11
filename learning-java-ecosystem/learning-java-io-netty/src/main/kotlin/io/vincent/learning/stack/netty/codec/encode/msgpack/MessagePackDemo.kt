package io.vincent.learning.stack.netty.codec.encode.msgpack

import org.msgpack.MessagePack
import org.msgpack.template.Templates

/**
 * Created by Vincent on 2018/8/30.
 * @author Vincent
 * @since 1.0, 2018/8/30
 */
object MessagePackDemo {

    fun <T> encode(input: T): ByteArray {
        val messagePack = MessagePack()
        // serialize
        return messagePack.write<T>(input)
    }

    fun decode(byteArray: ByteArray): List<String> {

        val messagePack = MessagePack()
        // deserialize
        return messagePack.read(byteArray, Templates.tList(Templates.TString))
    }
}