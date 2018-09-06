package io.vincent.learning.stack.netty.protocol.netty.server

import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelPipeline
import io.vincent.learning.stack.netty.protocol.netty.Header
import io.vincent.learning.stack.netty.protocol.netty.MessageType
import io.vincent.learning.stack.netty.protocol.netty.NettyMessage
import org.apache.commons.logging.LogFactory
import java.net.InetSocketAddress
import java.util.concurrent.ConcurrentHashMap

/**
 * Netty 协议，请求处理器
 *
 * Created by Vincent on 9/4/18.
 * @author Vincent
 * @since 1.0, 9/4/18
 */
class LoginAuthRespHandler : ChannelInboundHandlerAdapter() {

    private val nodeCheck = ConcurrentHashMap<String, Boolean>()
    private val whiteList = arrayOf("127.0.0.1", "192.168.10.64")

    /**
     * Calls [ChannelHandlerContext.fireChannelRead] to forward to
     * the next [ChannelHandler] in the [ChannelPipeline].
     *
     *
     * Sub-classes may override this method to change behavior.
     */
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val message = msg as NettyMessage

        // 如果是握手请求消息，处理，其它消息透传
        if (message.header.type == MessageType.LOGIN_REQ.value) {
            val nodeIndex = ctx.channel().remoteAddress().toString()
            val loginResp: NettyMessage?
            // 重复登陆，拒绝
            if (nodeCheck.containsKey(nodeIndex)) {
                loginResp = buildResponse((-1).toByte())
            } else {
                val address = ctx.channel().remoteAddress() as InetSocketAddress
                val ip = address.address.hostAddress
                var isOK = false
                for (WIP in whiteList) {
                    if (WIP == ip) {
                        isOK = true
                        break
                    }
                }
                loginResp = if (isOK)
                    buildResponse(0.toByte())
                else
                    buildResponse((-1).toByte())
                if (isOK)
                    nodeCheck[nodeIndex] = true
            }
            log.info("The login response is : ${loginResp.header.crcCode}, ${loginResp.header.sessionId}, ${loginResp.header.length}, ${loginResp.header.attachment} body [${loginResp.body}]")
            ctx.writeAndFlush(loginResp)
        } else {
            ctx.fireChannelRead(msg)
        }
    }

    private fun buildResponse(result: Byte): NettyMessage {
        return NettyMessage(Header(type = MessageType.LOGIN_RESP.value), result)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        nodeCheck.remove(ctx.channel().remoteAddress().toString())// 删除缓存
        ctx.close()
        ctx.fireExceptionCaught(cause)
    }

    companion object {

        private val log = LogFactory.getLog(LoginAuthRespHandler::class.java)
    }
}
