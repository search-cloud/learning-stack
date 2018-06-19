package io.vincent.learning.stack.netty.io

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.*

/**
 * @author Asion.
 * @since 2018/6/16.
 */
class TimeServerHandler(private val socket: Socket) : Runnable {



    override fun run() {

        try {
            BufferedReader(InputStreamReader(this.socket.getInputStream())).use { bf ->
                PrintWriter(this.socket.getOutputStream(), true).use { printWriter ->

                    var currentTIme: String
                    var body: String?
                    while (true) {
                        body = bf.readLine()
                        if (body == null) break
                        println("${Thread.currentThread().id}: [The time server receive order: $body]")

                        currentTIme = if ("Query Time Order".equals(body, ignoreCase = true))
                            Date(System.currentTimeMillis()).toString()
                        else
                            "Bad Order"
                        Thread.sleep(3000)
                        printWriter.println(currentTIme)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}
