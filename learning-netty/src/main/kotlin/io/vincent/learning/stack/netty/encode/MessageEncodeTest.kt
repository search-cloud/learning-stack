package io.vincent.learning.stack.netty.encode

import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.nio.ByteBuffer
import java.util.*

/**
 * Test Encode
 *
 * Created by Vincent on 2018/8/29.
 * @author Vincent
 * @since 1.0, 2018/8/29
 */
object MessageEncodeTest {
    @JvmStatic
    fun main(args: Array<String>) {
        val message = Message(1000, "Learning Netty!", 30, Date(), 2000.0)

        println("------------------------------------------------------")
        testEncodeByteSize(message)

        println("------------------------------------------------------")
        testEncodeSpend(message)
    }

    /**
     * Test encode byte size
     */
    private fun testEncodeByteSize(message: Message) {
        val out = ByteArrayOutputStream()
        val outputStream = ObjectOutputStream(out)
        outputStream.writeObject(message)
        outputStream.flush()
        outputStream.close()
        val byteArray = out.toByteArray()

        println("JDK serializable length is: ${byteArray.size} byte")
        out.close()

        println("------------------------------------------------------")
        val byteBuffer = ByteBuffer.allocate(1024)
        val codeC = message.codeC(byteBuffer)
        println("NIO byte array serializable length is: ${codeC.size} byte")

        println("------------------------------------------------------")
        println("Rate: ${byteArray.size / codeC.size}")
    }

    /**
     * Test encode spend
     */
    private fun testEncodeSpend(message: Message) {

        val loop = 10000000
        var out: ByteArrayOutputStream
        var outputStream: ObjectOutputStream
        val start = System.currentTimeMillis()
        for (i in 0..loop) {
            out = ByteArrayOutputStream()
            outputStream = ObjectOutputStream(out)
            outputStream.writeObject(message)
            outputStream.flush()
            outputStream.close()
            out.toByteArray()
            out.close()
        }
        val end = System.currentTimeMillis()
        println("JDK serializable time is: ${end - start} ms")
        println("------------------------------------------------------")
        val byteBuffer = ByteBuffer.allocate(1024)
        val cStart = System.currentTimeMillis()
        for (i in 0..loop) {
            message.codeC(byteBuffer)
        }
        val cEnd = System.currentTimeMillis()
        println("NIO byte array serializable time is: ${cEnd - cStart} ms")

        println("------------------------------------------------------")
        println("Rate: ${(end - start) / (cEnd - cStart)}")
    }
}