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
package io.vincent.learning.stack.netty.protocol.websocket.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpServerCodec
import io.netty.handler.stream.ChunkedWriteHandler

/**
 * Created by Vincent on 9/3/18.
 * @author Vincent
 * @since 1.0, 9/3/18
 */
class WebSocketServer {

    fun run(port: Int) {
        val bossGroup = NioEventLoopGroup()
        val workerGroup = NioEventLoopGroup()
        try {
            val b = ServerBootstrap()
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel::class.java)
                    .childHandler(object : ChannelInitializer<SocketChannel>() {
                        override fun initChannel(ch: SocketChannel) {
                            ch.pipeline()
                                    .addLast("http-codec", HttpServerCodec())
                                    .addLast("aggregator", HttpObjectAggregator(65536))
                                    .addLast("http-chunked", ChunkedWriteHandler())
                                    .addLast("handler", WebSocketServerHandler())
                        }
                    })

            val ch = b.bind(port).sync().channel()
            println("Web socket server started at port $port${'.'}")
            println("Open your browser and navigate to http://localhost:$port${'/'}")

            ch.closeFuture().sync()
        } finally {
            bossGroup.shutdownGracefully()
            workerGroup.shutdownGracefully()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            var port = 8080
            if (args.isNotEmpty()) {
                try {
                    port = Integer.parseInt(args[0])
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }

            }
            WebSocketServer().run(port)
        }
    }
}
