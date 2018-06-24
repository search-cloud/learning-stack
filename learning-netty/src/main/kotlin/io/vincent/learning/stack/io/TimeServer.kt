package io.vincent.learning.stack.io

import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

/**
 * @author Asion.
 * @since 2018/6/16.
 */
object TimeServer {

    @JvmStatic
    fun main(args: Array<String>) {
        var port = 8080
        if (args.isNotEmpty()) {
            try {
                port = Integer.valueOf(args[0])
            } catch (e: NumberFormatException) {
                //
            }

        }
        try {
            ServerSocket(port).use { serverSocket ->
                println("The time server is start in port: $port")
                var socket: Socket
                do {
                    socket = serverSocket.accept()
                    Thread(TimeServerHandler(socket)).start()
                } while (true)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}
