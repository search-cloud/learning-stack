package io.vincent.learning.stack.netty

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel

/**
 *
 * @author Asion.
 * @since 2018/6/24.
 */
class TimeServer {

    fun bind(port: Int) {
        val bossGroup = NioEventLoopGroup()
        val workerGroup = NioEventLoopGroup()
        val bootstrap = ServerBootstrap()
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(ChildChannelHandler())

        // binding port
        val future = bootstrap.bind(port).sync()
        // waiting server port close
        future.channel().closeFuture().sync()

        // shutdown gracefully
        bossGroup.shutdownGracefully()
        workerGroup.shutdownGracefully()
    }

    class ChildChannelHandler : ChannelInitializer<SocketChannel>() {
        override fun initChannel(ch: SocketChannel?) {
            ch!!.pipeline().addLast(TimeServerHandler())
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            TimeServer().bind(8080)
        }
    }
}