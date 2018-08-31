package io.vincent.learning.stack.netty.encode.msgpack.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import io.netty.handler.codec.LengthFieldPrepender
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.vincent.learning.stack.netty.encode.msgpack.MessagePackDecoder
import io.vincent.learning.stack.netty.encode.msgpack.MessagePackEncoder


/**
 * Created by Vincent on 2018/8/6.
 * @author Vincent
 * @since 1.0, 2018/8/6
 */
class MessagePackEchoServer {

    /**
     * 绑定端口
     */
    fun bind(port: Int) {
        // NIO Thread group
        val bossGroup = NioEventLoopGroup()
        val workerGroup = NioEventLoopGroup()
        val serverBootstrap = ServerBootstrap()
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(LoggingHandler(LogLevel.INFO))
                .childHandler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel?) {
                        ch!!.pipeline()
                                .addLast("frameDecoder", LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2))
                                .addLast("msgpack decode", MessagePackDecoder())
                                .addLast("frameEncoder", LengthFieldPrepender(2))
                                .addLast("msgpack encode", MessagePackEncoder())
                                .addLast(MessagePackEchoServerHandler())
                    }
                })

        // bind port and sync
        val channelFuture = serverBootstrap.bind(port).sync()
        channelFuture.channel().closeFuture().sync()

        // shutdown gracefully
        bossGroup.shutdownGracefully()
        workerGroup.shutdownGracefully()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            MessagePackEchoServer().bind(9999)
        }
    }
}