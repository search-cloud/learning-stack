package io.vincent.learning.stack.netty.decoder

import io.netty.bootstrap.Bootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.string.StringDecoder

/**
 * Created by Vincent on 2018/8/6.
 * @author Vincent
 * @since 1.0, 2018/8/6
 */
class EchoClient {

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
                        val delimiter = Unpooled.copiedBuffer(Delimiter.delimiter.toByteArray())
                        ch!!.pipeline()
                                .addLast(DelimiterBasedFrameDecoder(1024, delimiter))
                                .addLast(StringDecoder()).addLast(EchoClientHandler())
                    }
                })

        val channelFuture = bootstrap.connect(host, port).sync()
        channelFuture.channel().closeFuture().sync()

        group.shutdownGracefully()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            EchoClient().connect(8080, "127.0.0.1")
        }
    }
}