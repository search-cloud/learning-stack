package io.vincent.learning.stack.algorithm.leetcode

/**
 * One way linked list.
 *
 * @author Vincent
 * @since 1.0, 1/23/19
 */
open class LinkedList {

    private var head: ListNode? = null
    private var tail: ListNode? = null
    var size: Int = 0
        private set(value) {
            field = value
        }

    /**
     * add to head.
     */
    fun addToHead(e: Int) {
        head = ListNode(e, head)
        if (tail == null) {
            tail = head
        }
        size++
    }

    /**
     * add to last.
     */
    fun add(v: Int) {
        val newNode = ListNode(v, null)
        if (head == null) {
            head = newNode
            tail = head
        } else {
            tail!!.next = newNode
            tail = newNode
        }
        size++
    }

    /**
     * add to last.
     */
    fun add(e: ListNode) {
        if (head == null) {
            head = e
            tail = head
        } else {
            tail!!.next = e
            tail = e
        }
        size++
    }



    fun get(index: Int): Int? {
        return getNodeByIndex(index)?.value
    }

    fun remove(index: Int): Int? {
        if (index < 0 || index >= size) {
            println("index: $index")
            throw IndexOutOfBoundsException()
        }

        // 存放需要删除的节点
        val del: ListNode?
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

        return del?.value
    }

    fun head(): ListNode? {
        return head
    }

    fun tail(): ListNode? {
        return tail
    }

    private fun getNodeByIndex(index: Int): ListNode? {
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
            sb.append("${current.value}, ")
            current = current.next
        }

        return sb.delete(sb.length - 2, sb.length).append("]").toString()
    }
}
