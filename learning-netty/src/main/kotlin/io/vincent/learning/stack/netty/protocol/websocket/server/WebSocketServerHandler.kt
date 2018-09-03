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

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.DefaultFullHttpResponse
import io.netty.handler.codec.http.FullHttpRequest
import io.netty.handler.codec.http.FullHttpResponse
import io.netty.handler.codec.http.HttpHeaderUtil.isKeepAlive
import io.netty.handler.codec.http.HttpHeaderUtil.setContentLength
import io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST
import io.netty.handler.codec.http.HttpVersion.HTTP_1_1
import io.netty.handler.codec.http.websocketx.*
import io.netty.util.CharsetUtil
import org.slf4j.LoggerFactory

/**
 * Created by Vincent on 9/3/18.
 * @author Vincent
 * @since 1.0, 9/3/18
 */
class WebSocketServerHandler : SimpleChannelInboundHandler<Any>() {

    private var handshaker: WebSocketServerHandshaker? = null

    public override fun messageReceived(ctx: ChannelHandlerContext, msg: Any) {
        // 传统的HTTP接入
        if (msg is FullHttpRequest) {
            handleHttpRequest(ctx, msg)
        } else if (msg is WebSocketFrame) {
            handleWebSocketFrame(ctx, msg)
        }// WebSocket接入
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

    private fun handleHttpRequest(ctx: ChannelHandlerContext, req: FullHttpRequest) {

        // 如果HTTP解码失败，返回HTTP异常
        if (!req.decoderResult().isSuccess || "websocket" != req.headers().get("Upgrade")) {
            sendHttpResponse(ctx, req, DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST))
            return
        }

        // 构造握手响应返回，本机测试
        val wsFactory =
                WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false)
        handshaker = wsFactory.newHandshaker(req)
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel())
        } else {
            handshaker!!.handshake(ctx.channel(), req)
        }
    }

    private fun handleWebSocketFrame(ctx: ChannelHandlerContext, frame: WebSocketFrame) {

        // 判断是否是关闭链路的指令
        if (frame is CloseWebSocketFrame) {
            handshaker!!.close(ctx.channel(),
                    frame.retain() as CloseWebSocketFrame)
            return
        }
        // 判断是否是Ping消息
        if (frame is PingWebSocketFrame) {
            ctx.channel().write(
                    PongWebSocketFrame(frame.content().retain()))
            return
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (frame !is TextWebSocketFrame) {
            throw UnsupportedOperationException(String.format("%s frame types not supported", frame.javaClass.name))
        }

        // 返回应答消息
        val request = frame.text()
        if (logger.isTraceEnabled) {
            logger.trace(String.format("%s received %s", ctx.channel(), request))
        }
        ctx.channel()
                .write(TextWebSocketFrame("$request , 欢迎使用Netty WebSocket服务，现在时刻：${java.util.Date()}"))
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(WebSocketServerHandler::class.java)

        private fun sendHttpResponse(ctx: ChannelHandlerContext, req: FullHttpRequest, res: FullHttpResponse) {
            // 返回应答给客户端
            if (res.status().code() != 200) {
                val buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8)
                res.content().writeBytes(buf)
                buf.release()
                setContentLength(res, res.content().readableBytes().toLong())
            }

            // 如果是非Keep-Alive，关闭连接
            val f = ctx.channel().writeAndFlush(res)
            if (!isKeepAlive(req) || res.status().code() != 200) {
                f.addListener(ChannelFutureListener.CLOSE)
            }
        }
    }
}
