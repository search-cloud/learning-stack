package io.vincent.learning.stack.netty.encode

import java.io.Serializable
import java.nio.ByteBuffer
import java.util.*

/**
 * Created by Vincent on 2018/8/29.
 *
 * @author Vincent
 * @since 1.0, 2018/8/29
 */
class Message(private val id: Long,
              private val name: String,
              private val age: Int,
              private val birthDate: Date,
              private val account: Double) : Serializable {

    fun codeC(byteBuffer: ByteBuffer): ByteArray {
        byteBuffer.clear()
        byteBuffer.putDouble(this.account)
        byteBuffer.putLong(this.birthDate.time)
        byteBuffer.putInt(this.age)

        val bytes = this.name.toByteArray()
        byteBuffer.putInt(bytes.size)
        byteBuffer.put(bytes)

        byteBuffer.putLong(this.id)

        byteBuffer.flip()

        val result = ByteArray(byteBuffer.remaining())
        byteBuffer.get(result)
        return result
    }
}
