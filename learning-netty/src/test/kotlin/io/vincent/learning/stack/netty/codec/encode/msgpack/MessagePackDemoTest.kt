package io.vincent.learning.stack.netty.codec.encode.msgpack


import org.junit.Test

/**
 * Created by Vincent on 2018/8/30.
 *
 * @author Vincent
 * @since 1.0, 2018/8/30
 */
class MessagePackDemoTest {

    @Test
    fun encodeAndDecode() {
        val bytes = MessagePackDemo.encode()
        println(bytes?.size)
        if (bytes != null) {
            val decode = MessagePackDemo.decode(bytes)
            decode?.forEach { println(it) }
        }
    }
}
