package io.vincent.learning.stack.rpc

/**
 * Created by Vincent on 2019/3/21.
 *
 * @author Vincent
 * @since 1.0, 2019/3/21
 */
object RpcConsumer {

    @JvmStatic
    fun main(args: Array<String>) {
        val service = RpcServiceIntoDemo.reference(RpcHelloService::class.java, "127.0.0.1", 8080)
        val result = service.say("Vincent")
        println(result)

        for (i in 0 until 100) {
            val hello = service.say("World $i")
            println(hello)
            Thread.sleep(1000)
        }
    }
}
