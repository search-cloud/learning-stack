/*
 * Copyright 2013-2018 Lilinfeng.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vincent.learning.stack.netty.protocol.netty.client

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.timeout.ReadTimeoutHandler
import io.vincent.learning.stack.netty.protocol.netty.NettyConstant
import io.vincent.learning.stack.netty.protocol.netty.codec.NettyMessageDecoder
import io.vincent.learning.stack.netty.protocol.netty.codec.NettyMessageEncoder
import org.apache.commons.logging.LogFactory
import java.net.InetSocketAddress
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Netty 协议，client
 *
 * Created by Vincent on 9/4/18.
 * @author Vincent
 * @since 1.0, 9/4/18
 */
class NettyClient {

    private val executor = Executors.newScheduledThreadPool(1)

    internal var group: EventLoopGroup = NioEventLoopGroup()

    fun connect(port: Int, host: String) {

        // 配置客户端NIO线程组

        try {
            val b = Bootstrap()
            b.group(group).channel(NioSocketChannel::class.java)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(object : ChannelInitializer<SocketChannel>() {
                        public override fun initChannel(ch: SocketChannel) {
                            ch.pipeline()
                                    .addLast("MessageEncoder", NettyMessageEncoder())
                                    .addLast("NettyMessageDecoder", NettyMessageDecoder(1024 * 1024, 4, 4))
                                    .addLast("ReadTimeoutHandler", ReadTimeoutHandler(50))
                                    .addLast("LoginAuthHandler", LoginAuthReqHandler())
                                    .addLast("HeartBeatHandler", HeartBeatReqHandler())
                        }
                    })
            // 发起异步连接操作
            val future = b.connect(InetSocketAddress(host, port), InetSocketAddress(NettyConstant.LOCAL_IP, NettyConstant.LOCAL_PORT)).sync()
            // 当对应的channel关闭的时候，就会返回对应的channel。
            // Returns the ChannelFuture which will be notified when this channel is closed. This method always returns the same future instance.
            future.channel().closeFuture().sync()
        } finally {
            // 所有资源释放完成之后，清空资源，再次发起重连操作
            executor.execute {
                try {
                    TimeUnit.SECONDS.sleep(1)
                    try {
                        connect(NettyConstant.REMOTE_PORT, NettyConstant.REMOTE_IP)// 发起重连操作
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {

        private val log = LogFactory.getLog(NettyClient::class.java)

        /**
         * @param args
         * @throws Exception
         */
        @JvmStatic
        fun main(args: Array<String>) {
            NettyClient().connect(NettyConstant.REMOTE_PORT, NettyConstant.REMOTE_IP)
        }
    }

}
