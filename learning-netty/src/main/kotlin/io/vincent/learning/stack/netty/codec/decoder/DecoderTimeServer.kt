package io.vincent.learning.stack.netty.codec.decoder

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.LineBasedFrameDecoder
import io.netty.handler.codec.string.StringDecoder

/**
 *
 * @author Vincent.
 * @since 2018/08/06.
 */
class DecoderTimeServer {

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
        override fun initChannel(socketChannel: SocketChannel?) {
            socketChannel!!.pipeline().addLast(LineBasedFrameDecoder(1024))
            socketChannel.pipeline().addLast(StringDecoder())
            socketChannel.pipeline().addLast(DecoderTimeServerHandler())
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            DecoderTimeServer().bind(8080)
        }
    }
}