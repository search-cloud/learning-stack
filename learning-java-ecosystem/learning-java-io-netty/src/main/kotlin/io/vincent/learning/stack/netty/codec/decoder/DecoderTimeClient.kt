package io.vincent.learning.stack.netty.codec.decoder

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.LineBasedFrameDecoder
import io.netty.handler.codec.string.StringDecoder

/**
 *
 * @author Vincent.
 * @since 2018/8/6.
 */
class DecoderTimeClient {
    fun connect(port: Int, host: String) {
        val group = NioEventLoopGroup()
        val bootstrap = Bootstrap()
        bootstrap.group(group)
                .channel(NioSocketChannel::class.java)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(socketChannel: SocketChannel?) {
                        socketChannel!!.pipeline().addLast(LineBasedFrameDecoder(1024))
                        socketChannel.pipeline().addLast(StringDecoder())
                        socketChannel.pipeline().addLast(DecoderTimeClientHandler())
                    }
                })
        val future = bootstrap.connect(host, port).sync()
        future.channel().closeFuture().sync()

        group.shutdownGracefully()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            DecoderTimeClient().connect(8080, "127.0.0.1")
        }
    }
}