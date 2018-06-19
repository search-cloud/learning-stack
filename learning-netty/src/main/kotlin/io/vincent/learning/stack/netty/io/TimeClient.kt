package io.vincent.learning.stack.netty.io

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.time.Instant
import java.util.*
import java.util.concurrent.Executors

/**
 * @author Asion.
 * @since 2018/6/16.
 */
object TimeClient {
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
            val threadPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2)
            Socket("127.0.0.1", port).use { socket ->
                BufferedReader(InputStreamReader(socket.getInputStream())).use { bf ->
                    PrintWriter(socket.getOutputStream(), true).use { printWriter ->
                        for (i in 0.. 100) {
                            threadPool.execute { request(printWriter, bf, i) }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun request(printWriter: PrintWriter, bf: BufferedReader, i: Int) {
        val date = Date.from(Instant.now())

        printWriter.println("Query Time Order")

        println("Send order to server succeed. Numbers: $i")
        val resp = bf.readLine()

        println("Client time is: $date")
        println("Server time is: $resp")
    }
}
