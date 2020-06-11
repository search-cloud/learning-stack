package io.vincent.learning.stack.rpc

/**
 * Created by Vincent on 2019/3/21.
 *
 * @author Vincent
 * @since 1.0, 2019/3/21
 */
object RpcProvider {

    @JvmStatic
    fun main(args: Array<String>) {
        RpcServiceIntoDemo.export(RpcHelloServiceImpl())
    }
}
