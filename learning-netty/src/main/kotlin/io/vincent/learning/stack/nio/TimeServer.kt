package io.vincent.learning.stack.nio

/**
 *
 * @author Asion.
 * @since 2018/6/18.
 */
object TimeServer {
    @JvmStatic
    fun main(args: Array<String>) {
        var port = 8080
        if (args.isNotEmpty()) {
            port = Integer.valueOf(args[0])
        }
        val timerServer = MultiplexerTimerServer(port)
        Thread(timerServer, "NIO-MultiplexerTimeServer-001").start()
//        Thread(timerServer, "NIO-MultiplexerTimeServer-002").start()
    }
}