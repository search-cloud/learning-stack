package io.vincent.learning.stack.algorithm.leetcode

/**
 * Created by Vincent on 2019/3/24.
 * @author Vincent
 * @since 1.0, 2019/3/24
 */
object LinkedListUtils {
    @JvmStatic
    fun buildLinkedList(n: Int): LinkedList {
        val list = LinkedList()

        for (i in 1..n) {
            list.add(i)
        }

        return list
    }

    @JvmStatic
    fun printList(head: ListNode?) {
        var node = head
        while (node != null) {
            print("${node.value}->")
            node = node.next
        }
        println("NULL")
    }

}
