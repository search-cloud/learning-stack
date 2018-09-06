package io.vincent.learning.stack.netty.protocol.netty.codec

import io.netty.buffer.ByteBuf
import org.jboss.marshalling.ByteOutput

import java.io.IOException

/**
 * [ByteOutput] implementation which writes the data to a [ByteBuf]
 *
 *
 */
internal class ChannelBufferByteOutput
/**
 * Create a new instance which use the given [ByteBuf]
 */
(
        /**
         * Return the [ByteBuf] which contains the written content
         *
         */
        val buffer: ByteBuf) : ByteOutput {

    @Throws(IOException::class)
    override fun close() {
        // Nothing to do
    }

    @Throws(IOException::class)
    override fun flush() {
        // nothing to do
    }

    @Throws(IOException::class)
    override fun write(b: Int) {
        buffer.writeByte(b)
    }

    @Throws(IOException::class)
    override fun write(bytes: ByteArray) {
        buffer.writeBytes(bytes)
    }

    @Throws(IOException::class)
    override fun write(bytes: ByteArray, srcIndex: Int, length: Int) {
        buffer.writeBytes(bytes, srcIndex, length)
    }
}
