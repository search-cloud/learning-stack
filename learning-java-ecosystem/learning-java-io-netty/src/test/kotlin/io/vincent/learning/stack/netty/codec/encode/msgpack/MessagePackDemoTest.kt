package io.vincent.learning.stack.netty.codec.encode.msgpack

import kotlin.test.Test

/**
 * Created by Vincent on 2018/8/30.
 *
 * @author Vincent
 * @since 1.0, 2018/8/30
 */
class MessagePackDemoTest {

    @Test
    fun encodeAndDecode() {
        val objList = arrayListOf("msgpack", "test", "study")
        val bytes = MessagePackDemo.encode(objList)
        println(bytes.size)
        val decode = MessagePackDemo.decode(bytes)
        decode.forEach { println(it) }
    }
}
