package io.vincent.learning.stack.netty.codec.decoder

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler

/**
 * Created by Vincent on 2018/8/6.
 * @author Vincent
 * @since 1.0, 2018/8/6
 */
class EchoServer {

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
                        val delimiter = Unpooled.copiedBuffer(Delimiter.delimiter.toByteArray())
                        ch!!.pipeline()
                                .addLast(DelimiterBasedFrameDecoder(1024, delimiter))
//                                .addLast(FixedLengthFrameDecoder(20))
                                .addLast(StringDecoder())
                                .addLast(EchoServerHandler())
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
            EchoServer().bind(8080)
        }
    }
}