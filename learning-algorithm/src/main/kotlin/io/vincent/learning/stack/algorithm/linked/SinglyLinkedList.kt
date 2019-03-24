package io.vincent.learning.stack.algorithm.linked

/**
 * One way linked list.
 *
 * @author Vincent
 * @since 1.0, 1/23/19
 */
open class SinglyLinkedList<T> {

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    var size: Int = 0
        private set(value) {
            field = value
        }

    /**
     * add to head.
     */
    fun addToHead(e: T?) {
        head = Node(e, head)
        if (tail == null) {
            tail = head
        }
        size++
    }

    /**
     * add to last.
     */
    fun add(e: T?) {
        val newNode = Node(e, null)
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

    fun remove(index: Int): T? {
        if (index < 0 || index >= size) {
            println("index: $index")
            throw IndexOutOfBoundsException()
        }

        // 存放需要删除的节点
        val del: Node<T>?
        if (index == 0) {
            del = head
            head = head!!.next
        } else {
            val prev = getNodeByIndex(index - 1)
            del = prev!!.next
            prev.next = del!!.next
            del.next = null
        }
        size--

        return del?.data
    }

    fun head(): Node<T>? {
        return head
    }

    private fun getNodeByIndex(index: Int): Node<T>? {
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
