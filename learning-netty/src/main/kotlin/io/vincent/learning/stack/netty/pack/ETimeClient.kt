package io.vincent.learning.stack.netty.pack

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel

/**
 *
 * @author Asion.
 * @since 2018/6/24.
 */
class ETimeClient {
    fun connect(port: Int, host: String) {
        val group = NioEventLoopGroup()
        val bootstrap = Bootstrap()
        bootstrap.group(group)
                .channel(NioSocketChannel::class.java)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel?) {
                        ch!!.pipeline().addLast(ETimeClientHandler())
                    }
                })
        val future = bootstrap.connect(host, port).sync()
        future.channel().closeFuture().sync()

        group.shutdownGracefully()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            ETimeClient().connect(8080, "127.0.0.1")
        }
    }
}