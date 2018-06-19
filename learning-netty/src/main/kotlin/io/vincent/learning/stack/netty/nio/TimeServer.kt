package io.vincent.learning.stack.netty.nio

/**
 *
 * @author Asion.
 * @since 2018/6/18.
 */
class TimeServer {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            var port = 8080
            if (args.isNotEmpty()) {
                try {
                    port = Integer.valueOf(args[0])
                } catch (e: NumberFormatException) {

                }
            }
        }
    }
}