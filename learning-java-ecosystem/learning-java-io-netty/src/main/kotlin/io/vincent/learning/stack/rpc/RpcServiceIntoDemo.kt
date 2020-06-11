package io.vincent.learning.stack.rpc

import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.reflect.Proxy
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket


/**
 * RPC (Remote Process Callable) intro demo.
 *
 * 1. Service
 * 2. Consumer
 * 3. IO / Serializable
 *
 * Socket ServerSocket
 *
 * @author Vincent
 * @since 1.0, 2019/3/21
 */
object RpcServiceIntoDemo {

    /**
     * 发布服务
     */
    fun export(service: Any) {
        // server
        val serverSocket = ServerSocket()
        serverSocket.bind(InetSocketAddress("127.0.0.1", 8080))
        while (true) {
            val socket = serverSocket.accept()
            Thread {
                socket.use {
                    val input = ObjectInputStream(socket.getInputStream())
                    input.use {
                        // 从输入流中取出以下数据: 方法名称, 方法参数类型, 方法参数
                        val methodName = input.readUTF()
                        val parameterTypes = input.readObject() as Array<Class<*>>
                        val args = input.readObject() as Array<Any>

                        // JDK 反射调用方法, 将返回结果写到输出流中
                        val output = ObjectOutputStream(socket.getOutputStream())
                        try {
                            val method = service.javaClass.getMethod(methodName, *parameterTypes)
                            val result = method.invoke(service, *args)

                            output.writeObject(result)
                        } catch (t: Throwable) {
                            output.writeObject(t)
                        } finally {
                            output.close()
                        }
                    }
                }
            }.start()
        }

    }

    /**
     * 引用服务
     */
    fun <T> reference(interfaceClass: Class<T>, host: String, port: Int): T {
        return Proxy.newProxyInstance(interfaceClass.classLoader, arrayOf<Class<*>>(interfaceClass)) { proxy, method, arguments ->
            Socket(host, port).use { socket ->
                ObjectOutputStream(socket.getOutputStream()).use { output ->
                    // 向输出流中写入相应的数据
                    output.writeUTF(method.name)
                    output.writeObject(method.parameterTypes)
                    output.writeObject(arguments)

                    ObjectInputStream(socket.getInputStream()).use { input ->
                        val result = input.readObject()
                        if (result is Throwable) {
                            throw result
                        }
                        return@newProxyInstance result
                    }
                }
            }
        } as T
    }
}
