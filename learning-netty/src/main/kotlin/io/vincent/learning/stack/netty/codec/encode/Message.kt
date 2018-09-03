package io.vincent.learning.stack.netty.codec.encode

import java.nio.ByteBuffer
import java.util.*

/**
 * Created by Vincent on 2018/8/31.
 *
 * @author Vincent
 * @since 1.0, 2018/8/31
 */
@org.msgpack.annotation.Message
class Message(private var id: Long? = null, private var name: String? = null, private var age: Int? = null, private var birthDate: Date? = null, private var account: Double? = null) {

    fun codeC(byteBuffer: ByteBuffer): ByteArray {
        byteBuffer.clear()
        byteBuffer.putDouble(this.account!!)
        byteBuffer.putLong(this.birthDate!!.time)
        byteBuffer.putInt(this.age!!)

        val bytes = this.name!!.toByteArray()
        byteBuffer.putInt(bytes.size)
        byteBuffer.put(bytes)

        byteBuffer.putLong(this.id!!)

        byteBuffer.flip()

        val result = ByteArray(byteBuffer.remaining())
        byteBuffer.get(result)
        return result
    }

}
