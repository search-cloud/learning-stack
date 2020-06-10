package io.vincent.learning.stack.rpc

/**
 * Created by Vincent on 2019/3/21.
 *
 * @author Vincent
 * @since 1.0, 2019/3/21
 */
class RpcHelloServiceImpl : RpcHelloService {
    override fun say(name: String): String {
        return "Hello $name"
    }
}
