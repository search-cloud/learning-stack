package io.vincent.learning.stack.netty.nio

import org.slf4j.LoggerFactory
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.SocketChannel

/**
 *
 * @author Asion.
 * @since 2018/6/19.
 */
class TimeClientHandler(private val host: String, private val port: Int, name: String) : Runnable {

    private val socketChannel: SocketChannel = SocketChannel.open()
    private val selector: Selector
    @Volatile
    private var stop: Boolean = false
    private var name: String = Thread.currentThread().id.toString()

    init {
        socketChannel.configureBlocking(false)
        selector = Selector.open()
        this.name = name
    }

    override fun run() {
        // 1. connect to server
        doConnect()
        // 2. handleMassage
        while (!stop) {
            selector.select(1000)
            val selectedKeys = selector.selectedKeys()
            val it = selectedKeys.iterator()
            var key: SelectionKey
            while (it.hasNext()) {
                key = it.next()
                it.remove()
                if (key != null) {
                    try {
                        handleInput(key)
                    } catch (e: Exception) {
                        logger.error("", e)
                        key.cancel()
                        if (key.channel() != null) {
                            key.channel().close()
                        }
                    }
                }
            }
        }
    }

    private fun handleInput(key: SelectionKey) {
        if (key.isValid) {
            val channel = key.channel() as SocketChannel
            if (key.isConnectable) {
                if (channel.finishConnect()) {
                    channel.register(selector, SelectionKey.OP_READ)
                    doWrite(channel)
                } else {
                    System.exit(1) // connection fail thread quit
                }
            }
            if (key.isReadable) {
                val readBuffer = ByteBuffer.allocate(1024)
                val readBytes = channel.read(readBuffer)
                if (readBytes > 0) {
                    readBuffer.flip()
                    val bytes = ByteArray(readBuffer.remaining())
                    readBuffer.get(bytes)
                    val body = String(bytes, Charsets.UTF_8)
                    logger.info("Server time is: $body, Client: $name")
                    this.stop = true
                } else if (readBytes < 0) {
                    key.cancel()
                    channel.close()
                } else {
                    // ignore zero byte
                }
            }
        }
    }

    private fun doConnect() {
        val connected = socketChannel.connect(InetSocketAddress(host, port))
        if (connected) {
            // connected register to read
            socketChannel.register(selector, SelectionKey.OP_READ)
            doWrite(socketChannel)
        } else {
            // 重新获取连接
            socketChannel.register(selector, SelectionKey.OP_CONNECT)
        }
    }

    private fun doWrite(socketChannel: SocketChannel) {
        val bytes = "Query Time Order".toByteArray()
        val writeBuffer = ByteBuffer.allocate(1024)
        writeBuffer.put(bytes)
        writeBuffer.flip()
        socketChannel.write(writeBuffer)
        if (!writeBuffer.hasRemaining()) {
            logger.info("Send order to server succeed. $name")
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(TimeClientHandler::class.java)
    }
}