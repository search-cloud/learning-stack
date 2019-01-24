package io.vincent.learning.stack.algorithm.linked

/**
 * Created by Vincent on 1/24/19.
 * @author Vincent
 * @since 1.0, 1/24/19
 */
class DoublyLinkedList<T> {

    /**
     * Node.
     */
    private inner class Node(var data: T? = null, var prev: Node? = null, var next: Node? = null)

    private var head: Node? = null
    private var tail: Node? = null
    var size: Int = 0
        private set(value) {
            field = value
        }

    /**
     * add to head.
     */
    fun addToHead(e: T?) {
        head = Node(e, null, head)
        if (tail == null) {
            tail = head
        } else {
            head!!.next!!.prev = head
        }
        size++
    }

    /**
     * add to last.
     */
    fun add(e: T?) {
        val newNode = Node(e, tail, null)
        if (head == null) {
            head = newNode
            tail = head
        } else {
            tail!!.next = newNode
            tail = newNode
        }
        size++
    }

    fun get(index: Int): T? {
        return getNodeByIndex(index)?.data
    }

    /**
     *
     */
    fun remove(index: Int): T? {
        if (index < 0 || index >= size) {
            println("index: $index")
            throw IndexOutOfBoundsException()
        }

        // 存放需要删除的节点
        val del: Node?
        if (index == 0) {
            del = head
            head = head!!.next
            head!!.prev = null
        } else {
            val prev = getNodeByIndex(index - 1)
            del = prev!!.next
            prev.next = del!!.next

            if (del.next != null) {
                del.next!!.prev = prev
            }

            del.next = null
            del.prev = null
        }
        size--

        return del?.data
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

    override fun toString(): String {

        if (size == 0) return "[]"
        val sb = StringBuilder("[")

        var current = head
        while (current != null) {
            sb.append("${current.data}, ")
            current = current.next
        }

        return sb.delete(sb.length - 2, sb.length).append("]").toString()
    }
}
