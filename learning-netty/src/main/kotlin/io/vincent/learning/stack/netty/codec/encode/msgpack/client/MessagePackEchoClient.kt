package io.vincent.learning.stack.netty.codec.encode.msgpack.client

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import io.netty.handler.codec.LengthFieldPrepender
import io.vincent.learning.stack.netty.codec.encode.msgpack.MessagePackDecoder
import io.vincent.learning.stack.netty.codec.encode.msgpack.MessagePackEncoder


/**
 * Created by Vincent on 2018/8/6.
 * @author Vincent
 * @since 1.0, 2018/8/6
 */
class MessagePackEchoClient {

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
                                .addLast("frameDecoder", LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2))
                                .addLast("msgpack decode", MessagePackDecoder())
                                .addLast("frameEncoder", LengthFieldPrepender(2))
                                .addLast("msgpack encode", MessagePackEncoder())
                                .addLast(MessagePackEchoClientHandler(1000))
                    }
                })

        val channelFuture = bootstrap.connect(host, port).sync()
        channelFuture.channel().closeFuture().sync()

        group.shutdownGracefully()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            MessagePackEchoClient().connect(9999, "127.0.0.1")
        }
    }
}