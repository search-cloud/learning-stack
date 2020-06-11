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

import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelPipeline
import io.vincent.learning.stack.netty.protocol.netty.Header
import io.vincent.learning.stack.netty.protocol.netty.MessageType
import io.vincent.learning.stack.netty.protocol.netty.NettyMessage
import org.apache.commons.logging.LogFactory

/**
 * Netty 协议，client请求处理器
 *
 * Created by Vincent on 9/4/18.
 * @author Vincent
 * @since 1.0, 9/4/18
 */
class LoginAuthReqHandler : ChannelInboundHandlerAdapter() {

    /**
     * Calls [ChannelHandlerContext.fireChannelActive] to forward to the
     * next [ChannelHandler] in the [ChannelPipeline].
     *
     *
     * Sub-classes may override this method to change behavior.
     */
    override fun channelActive(ctx: ChannelHandlerContext) {
        ctx.writeAndFlush(buildLoginReq())
    }

    /**
     * Calls [ChannelHandlerContext.fireChannelRead] to forward to
     * the next [ChannelHandler] in the [ChannelPipeline].
     *
     *
     * Sub-classes may override this method to change behavior.
     */
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val message = msg as NettyMessage

        // 如果是握手应答消息，需要判断是否认证成功
        if (message.header.type == MessageType.LOGIN_RESP.value) {
            val loginResult = message.body as Byte
            if (loginResult != 0.toByte()) {
                // 握手失败，关闭连接
                ctx.close()
            } else {
                log.info("Login is ok : $message")
                ctx.fireChannelRead(msg)
            }
        } else
            ctx.fireChannelRead(msg)
    }

    private fun buildLoginReq(): NettyMessage {
        return NettyMessage(Header(type = MessageType.LOGIN_REQ.value), null)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        ctx.fireExceptionCaught(cause)
    }

    companion object {

        private val log = LogFactory.getLog(LoginAuthReqHandler::class.java)
    }
}
