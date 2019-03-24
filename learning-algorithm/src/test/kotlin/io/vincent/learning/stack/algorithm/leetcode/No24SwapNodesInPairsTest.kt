package io.vincent.learning.stack.algorithm.leetcode

import org.junit.Test

/**
 * Created by Vincent on 2019/3/24.
 *
 * @author Vincent
 * @since 1.0, 2019/3/24
 */
class No24SwapNodesInPairsTest {

    @Test
    fun swapPairs() {
        val list = buildLinkedList(4)

        val head = list.head()
        println("before reverse: $head")
        printList(head)

        val node = No24SwapNodesInPairs.swapPairs(list.head())
        println("after reverse: $node")
        printList(node)
    }

    @Test
    fun swapPairs1() {
        val list = buildLinkedList(8)

        val head = list.head()
        println("before reverse: $head")
        printList(head)

        val node = No24SwapNodesInPairs.swapPairs(list.head())
        println("after reverse: $node")
        printList(node)
    }

    @Test
    fun swapPairs2() {
        val list = buildLinkedList(3)

        val head = list.head()
        println("before reverse: $head")
        printList(head)

        val node = No24SwapNodesInPairs.swapPairs(list.head())
        println("after reverse: $node")
        printList(node)
    }

    private fun buildLinkedList(n: Int): LinkedList {
        val list = LinkedList()

        for (i in 1..n) {
            list.add(i)
        }

        return list
    }

    private fun printList(head: ListNode?) {
        var node = head
        while (node != null) {
            print("${node.value}->")
            node = node.next
        }
        println("NULL")
    }
}
