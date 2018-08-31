package io.vincent.learning.stack.netty.encode.msgpack

import org.msgpack.MessagePack
import org.msgpack.template.Templates

/**
 * Created by Vincent on 2018/8/30.
 * @author Vincent
 * @since 1.0, 2018/8/30
 */
object MessagePackDemo {

    fun encode(): ByteArray? {
        val objList = arrayListOf("msgpack", "test", "study")
        val messagePack = MessagePack()
        // serialize
        return messagePack.write(objList)
    }

    fun decode(byteArray: ByteArray): List<String>? {

        val messagePack = MessagePack()
        // deserialize
        return messagePack.read<MutableList<String>?>(byteArray, Templates.tList(Templates.TString))
    }
}