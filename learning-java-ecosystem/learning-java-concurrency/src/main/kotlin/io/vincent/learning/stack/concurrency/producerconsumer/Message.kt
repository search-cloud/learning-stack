package io.vincent.learning.stack.concurrency.producerconsumer

/**
 * Thread's communication Message.
 *
 * @author Vincent
 * @since 1.0, 12/24/18
 */
class Message(var id: Long? = null, var head: String? = null, var body: String? = null) {
    override fun toString(): String {
        return "Message: {id=$id, head=$head, body=$body}"
    }
}
