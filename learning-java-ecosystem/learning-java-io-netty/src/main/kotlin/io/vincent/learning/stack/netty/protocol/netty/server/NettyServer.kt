package io.vincent.learning.stack.netty.protocol.netty.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.timeout.ReadTimeoutHandler
import io.vincent.learning.stack.netty.protocol.netty.NettyConstant
import io.vincent.learning.stack.netty.protocol.netty.codec.NettyMessageDecoder
import io.vincent.learning.stack.netty.protocol.netty.codec.NettyMessageEncoder
import org.apache.commons.logging.LogFactory

/**
 * Netty 协议server
 *
 * Created by Vincent on 9/4/18.
 * @author Vincent
 * @since 1.0, 9/4/18
 */
class NettyServer {

    fun bind() {
        // 配置服务端的NIO线程组
        val bossGroup = NioEventLoopGroup()
        val workerGroup = NioEventLoopGroup()
        try {
            val b = ServerBootstrap()
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel::class.java)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(LoggingHandler(LogLevel.DEBUG))
                    .childHandler(object : ChannelInitializer<SocketChannel>() {
                        public override fun initChannel(ch: SocketChannel) {
                            ch.pipeline()
                                    .addLast("NettyMessageDecoder", NettyMessageDecoder(1024 * 1024, 4, 4))
                                    .addLast("NettyMessageEncoder", NettyMessageEncoder())
                                    .addLast("ReadTimeoutHandler", ReadTimeoutHandler(50))
                                    .addLast("LoginAuthRespHandler", LoginAuthRespHandler())
                                    .addLast("HeartBeatHandler", HeartBeatRespHandler())
                        }
                    })
            // 绑定端口，同步等待成功
            val future = b.bind(NettyConstant.REMOTE_IP, NettyConstant.REMOTE_PORT).sync()
            log.info("Netty server start ok : " + (NettyConstant.REMOTE_IP + " : " + NettyConstant.REMOTE_PORT))
            future.channel().closeFuture().sync()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {
        private val log = LogFactory.getLog(NettyServer::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            NettyServer().bind()
        }
    }
}
