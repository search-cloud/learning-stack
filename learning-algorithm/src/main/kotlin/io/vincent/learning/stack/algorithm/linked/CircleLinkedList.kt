package io.vincent.learning.stack.algorithm.linked

/**
 * Circle linkded list.
 *
 * @author Vincent
 * @since 1.0, 1/24/19
 */
class CircleLinkedList<T> {
    /**
     * Node.
     */
    private inner class Node(var data: T? = null, var next: Node? = null)

    private var head: Node? = null
    private var tail: Node? = null
    var size: Int = 0
        private set(value) {
            field = value
        }

    /**
     * add to last.
     */
    fun add(e: T?) {
        val newNode = Node(e, null)
        if (head == null) {
            head = newNode
            tail = head
            tail!!.next = head
        } else {
            tail!!.next = newNode
            tail = newNode
            newNode.next = head
        }
        size++
    }

    /**
     * get by index.
     */
    fun get(index: Int): T? {
        return getNodeByIndex(index)?.data
    }

    fun next(e: T?): T? {
        val index = indexOf(e)
        if (index == -1) return null
        return getNodeByIndex(index + 1)?.data
    }

    private fun indexOf(e: T?): Int {
        if (e == null) return -1
        var current = head
        var index = 0
        while (index < size -1 && current != null) {
            if (current.data == e) {
                return index
            }
            current = current.next
            index++
        }
        return -1
    }

    private fun getNodeByIndex(index: Int): Node? {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException()
        }
        var current = head
        for (i in 0..size) {
            if (current == null) {
                break
            }
            if (i == index) {
                return current
            } else {
                current = current.next
            }
        }
        return null
    }

}
