package io.vincent.learning.stack.rpc

/**
 * RPC hello service interface.
 *
 * @author Vincent
 * @since 1.0, 2019/3/21
 */
interface RpcHelloService {

    fun say(name: String): String
}
