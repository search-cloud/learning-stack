package io.vincent.learning.stack.netty.protocol.netty

import java.io.Serializable

/**
 * Netty 协议消息
 * 包括：消息头和消息体
 *
 * Created by Vincent on 9/4/18.
 * @author Vincent
 * @since 1.0, 9/4/18
 */
open class NettyMessage(var header: Header = Header(), var body: Any? = null) : Serializable {

    operator fun component1(): NettyMessage {
        return this
    }

}