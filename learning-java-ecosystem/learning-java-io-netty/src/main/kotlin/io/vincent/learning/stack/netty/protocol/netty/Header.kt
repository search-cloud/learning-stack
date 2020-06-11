package io.vincent.learning.stack.netty.protocol.netty

import java.io.Serializable

/**
 * Netty 协议头
 *
 * Created by Vincent on 9/4/18.
 * @author Vincent
 * @since 1.0, 9/4/18
 */
open class Header(
        /**
         * 消息校验码，由3部分组成
         * 1. 0xABEF 表明是Netty协议，2 byte
         * 2. major version: 1~255, 1 byte
         * 3. minor version: 1~255, 1 byte
         *
         * crcCode = 0xABEF + [major version] + [minor version]
         */
        var crcCode: Int = 0xABEF101,
        /**
         * 消息长度
         */
        var length: Int = 0,
        /**
         * 集群节点全局唯一id
         */
        var sessionId: Long = System.currentTimeMillis(),
        /**
         * 消息类型
         * @see MessageType
         */
        var type: Byte = MessageType.ONE_WAY.value,
        /**
         * 消息优先级，0~255
         */
        var priority: Byte = 0.toByte(),
        /**
         * 消息头扩展
         */
        var attachment: Map<String, Any>? = null
) : Serializable