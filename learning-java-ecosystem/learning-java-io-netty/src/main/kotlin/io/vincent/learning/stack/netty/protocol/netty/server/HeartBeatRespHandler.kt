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
package io.vincent.learning.stack.netty.protocol.netty.server

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.vincent.learning.stack.netty.protocol.netty.Header
import io.vincent.learning.stack.netty.protocol.netty.MessageType
import io.vincent.learning.stack.netty.protocol.netty.NettyMessage
import org.apache.commons.logging.LogFactory

/**
 * Netty 协议，请求处理器
 *
 * Created by Vincent on 9/4/18.
 * @author Vincent
 * @since 1.0, 9/4/18
 */
class HeartBeatRespHandler : ChannelInboundHandlerAdapter() {

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val message = msg as NettyMessage
        // 返回心跳应答消息
        when {
            message.header.type == MessageType.HEARTBEAT_REQ.value -> {
                log.info("Receive client heart beat message : ---> $message")
                val heartBeat = buildHeatBeat()
                log.info("Send heart beat response message to client : ---> $heartBeat")
                ctx.writeAndFlush(heartBeat)
            }
            else -> ctx.fireChannelRead(msg)
        }
    }

    private fun buildHeatBeat(): NettyMessage {
        return NettyMessage(Header(type = MessageType.HEARTBEAT_RESP.value), null)
    }

    companion object {
        private val log = LogFactory.getLog(HeartBeatRespHandler::class.java)
    }

}
