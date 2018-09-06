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

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.vincent.learning.stack.netty.protocol.netty.Header
import io.vincent.learning.stack.netty.protocol.netty.MessageType
import io.vincent.learning.stack.netty.protocol.netty.NettyMessage
import org.apache.commons.logging.LogFactory
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/**
 * Netty 协议，client请求处理器
 *
 * Created by Vincent on 9/4/18.
 * @author Vincent
 * @since 1.0, 9/4/18
 */
class HeartBeatReqHandler : ChannelInboundHandlerAdapter() {

    @Volatile
    private var heartBeat: ScheduledFuture<*>? = null

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val message = msg as NettyMessage
        // 握手成功，主动发送心跳消息
        when {
            message.header.type == MessageType.LOGIN_RESP.value -> heartBeat =
                    ctx.executor().scheduleAtFixedRate(HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS)
            message.header.type == MessageType.HEARTBEAT_RESP.value ->
                log.info("Client receive server heart beat message : ---> $message")
            else -> ctx.fireChannelRead(msg)
        }
    }

    private inner class HeartBeatTask(private val ctx: ChannelHandlerContext) : Runnable {

        override fun run() {
            val heatBeat = buildHeatBeat()
            log.info("Client send heart beat messsage to server : ---> $heatBeat")
            ctx.writeAndFlush(heatBeat)
        }

        private fun buildHeatBeat(): NettyMessage {
            return NettyMessage(Header(type = MessageType.HEARTBEAT_REQ.value, attachment = null), null)
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        if (heartBeat != null) {
            heartBeat!!.cancel(true)
            heartBeat = null
        }
        ctx.fireExceptionCaught(cause)
    }

    companion object {
        private val log = LogFactory.getLog(HeartBeatReqHandler::class.java)
    }
}
