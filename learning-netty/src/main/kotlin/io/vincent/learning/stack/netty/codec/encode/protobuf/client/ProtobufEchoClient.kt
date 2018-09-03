package io.vincent.learning.stack.netty.codec.encode.protobuf.client

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.protobuf.ProtobufDecoder
import io.netty.handler.codec.protobuf.ProtobufEncoder
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender
import io.vincent.learning.stack.netty.codec.encode.protobuf.SearchResponseProto


/**
 * Created by Vincent on 2018/8/6.
 * @author Vincent
 * @since 1.0, 2018/8/6
 */
class ProtobufEchoClient {

    /**
     * 连接server
     */
    fun connect(port: Int, host: String) {
        val group = NioEventLoopGroup()
        val bootstrap = Bootstrap()
        bootstrap.group(group)
                .channel(NioSocketChannel::class.java)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel?) {
                        ch!!.pipeline()
                                .addLast(ProtobufVarint32FrameDecoder())
                                .addLast(ProtobufDecoder(SearchResponseProto.ItemSearchResponse.getDefaultInstance()))
                                .addLast(ProtobufVarint32LengthFieldPrepender())
                                .addLast(ProtobufEncoder())
                                .addLast(ProtobufEchoClientHandler())
                    }
                })

        val channelFuture = bootstrap.connect(host, port).sync()
        channelFuture.channel().closeFuture().sync()

        group.shutdownGracefully()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            ProtobufEchoClient().connect(9999, "127.0.0.1")
        }
    }
}