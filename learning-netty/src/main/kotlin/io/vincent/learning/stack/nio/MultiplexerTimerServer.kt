package io.vincent.learning.stack.nio

import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.util.*

/**
 *
 * @author Asion.
 * @since 2018/6/18.
 */
open class MultiplexerTimerServer(port: Int) : Runnable {


    private var selector: Selector? = null
    private lateinit var serverSocketChannel: ServerSocketChannel
    @Volatile
    private var stop: Boolean = false

    init {
        try {
            // 1. ServerSocketChannel
            serverSocketChannel = ServerSocketChannel.open()
            // 2. block false
            serverSocketChannel.configureBlocking(false)
            // 3. bind InetSocketAddress
            serverSocketChannel.socket().bind(InetSocketAddress(port), 1024)
            // 4. selector
            selector = Selector.open()
            // 5. register selector on accept key
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT)
            logger.info("The time server is start in port: $port")
        } catch (e: Exception) {
            e.printStackTrace()
            System.exit(1)
        }
    }

    open fun stop() {
        this.stop = true
    }

    override fun run() {
        while (!stop) {
            selector!!.select(1000)
            val selectedKeys = selector!!.selectedKeys()
            val it = selectedKeys.iterator()
            var key: SelectionKey
            while (it.hasNext()) {
                key = it.next()
                it.remove()
                try {
                    if (key != null) {
                        handleInput(key)
                    }
                } catch (e: Exception) {
                    logger.error("", e)
                    if (key != null) {
                        key.cancel()
                        if (key.channel() != null) {
                            key.channel().close()
                        }
                    }
                }

            }
        }
        if (selector != null) {
            try {
                selector!!.close()
            } catch (e: IOException) {
                logger.error("", e)
            }
        }
    }

    @Throws(IOException::class)
    private fun handleInput(key: SelectionKey) {
        if (key.isValid) {
            if (key.isAcceptable) {
                // Accept the new connection
                val socketChannel = key.channel() as ServerSocketChannel
                val channel = socketChannel.accept()
                channel.configureBlocking(false)
                // Add the new connection to the selector
                channel.register(selector, SelectionKey.OP_READ)
            }
            if (key.isReadable) {
                // Read the data
                val channel = key.channel() as SocketChannel
                val readBuffer = ByteBuffer.allocate(1024)
                val readBytes = channel.read(readBuffer)
                when {
                    readBytes > 0 -> {
                        readBuffer.flip()
                        val bytes = ByteArray(readBuffer.remaining())
                        readBuffer.get(bytes)
                        val body = String(bytes, Charsets.UTF_8)
                        logger.info("The time server receive order: $body")
                        val currentTIme = when {
                            "Query Time Order".equals(body, ignoreCase = true) -> Date(System.currentTimeMillis()).toString()
                            else -> "Bad Order"
                        }
                        Thread.sleep(3000)
                        doWrite(channel, currentTIme)
                    }
                    readBytes < 0 -> {
                        // close channel
                        key.cancel()
                        channel.close()
                    }
                    else -> {
                        // ignore zero byte
                    }
                }
            }
        }
    }

    private fun doWrite(channel: SocketChannel, message: String) {
        if (message.isNotEmpty()) {
            val bytes = message.toByteArray(Charsets.UTF_8)
            val writeBuffer = ByteBuffer.allocate(bytes.size)
            writeBuffer.put(bytes)
            writeBuffer.flip()
            channel.write(writeBuffer)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(MultiplexerTimerServer::class.java)
    }
}