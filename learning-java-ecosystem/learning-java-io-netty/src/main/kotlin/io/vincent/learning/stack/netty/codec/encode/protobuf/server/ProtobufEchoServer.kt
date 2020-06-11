package io.vincent.learning.stack.netty.codec.encode.protobuf.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.protobuf.ProtobufDecoder
import io.netty.handler.codec.protobuf.ProtobufEncoder
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.vincent.learning.stack.netty.codec.encode.protobuf.SearchRequestProto


/**
 * Created by Vincent on 2018/8/6.
 * @author Vincent
 * @since 1.0, 2018/8/6
 */
class ProtobufEchoServer {

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
                                .addLast(ProtobufVarint32FrameDecoder())
                                .addLast(ProtobufDecoder(SearchRequestProto.ItemSearchRequest.getDefaultInstance()))
                                .addLast(ProtobufVarint32LengthFieldPrepender())
                                .addLast(ProtobufEncoder())
                                .addLast(ProtobufEchoServerHandler())
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
            ProtobufEchoServer().bind(9999)
        }
    }
}