package io.vincent.learning.stack.netty.protocol.netty

/**
 * Created by Vincent on 9/4/18.
 * @author Vincent
 * @since 1.0, 9/4/18
 */
enum class MessageType(val value: Byte) {

    // 业务请求
    SERVICE_REQ(0.toByte()),
    // 业务相应
    SERVICE_RESP(1.toByte()),
    // 既是请求又是响应
    ONE_WAY(2.toByte()),
    // 握手请求
    LOGIN_REQ(3.toByte()),
    // 握手响应
    LOGIN_RESP(4.toByte()),
    // 心跳请求
    HEARTBEAT_REQ(5.toByte()),
    // 心跳响应
    HEARTBEAT_RESP(6.toByte());

}
