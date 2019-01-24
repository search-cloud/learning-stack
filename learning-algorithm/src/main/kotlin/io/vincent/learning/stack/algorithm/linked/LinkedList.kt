package io.vincent.learning.stack.algorithm.linked

/**
 * Created by Vincent on 1/23/19.
 * @author Vincent
 * @since 1.0, 1/23/19
 */
class LinkedList {

    /**
     * Created by Vincent on 11/29/18.
     * @author Vincent
     * @since 1.0, 11/29/18
     */
    private inner class Node(var data: Any) {
        var next: Node? = null
        var first: Node? = null
        var last: Node? = null
    }

    private var head: Node? = null
    private var tail: Node? = null
    private var next: Node? = null
    var size: Int = 0
        private set(value) {
            field = value
        }

    /**
     * Add data to Linked.
     */
    public fun add(data: Any) {

    }

    private fun add(node: Node) {
        if (size == 0) {
            head = node
            tail = node
            next = null
            size++
        } else {

        }
    }

    fun get(key: Any) {

    }

    fun remove() {

    }
}
