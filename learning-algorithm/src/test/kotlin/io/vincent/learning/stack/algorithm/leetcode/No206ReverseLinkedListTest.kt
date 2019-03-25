package io.vincent.learning.stack.algorithm.leetcode

import io.vincent.learning.stack.algorithm.leetcode.linked.LinkedList
import io.vincent.learning.stack.algorithm.leetcode.linked.LinkedListUtils
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Reverse a singly linked list.
 *
 * Example:
 *
 * Input: 1->2->3->4->5->NULL
 * Output: 5->4->3->2->1->NULL
 * Follow up:
 *
 * A linked list can be reversed either iteratively or recursively. Could you implement both?
 *
 * @author Vincent
 * @since 1.0, 2019/3/24
 */
class No206ReverseLinkedListTest {

    @Test
    fun reverse() {
        val list = LinkedList()

        for (i in 0..10) {
            list.add(i)
        }

        for (i in 0 until list.size) {
            println(list.get(i))
        }

        var node = No206ReverseLinkedList.reverse(list.head())
        assertEquals(10, node?.value)
        println("after reverse: $node")
        while (node?.next != null) {
            println(node.value)
            node = node.next
        }
    }

    @Test
    fun recursivelyReverse() {
        val list = LinkedListUtils.buildLinkedList(4)

        val head = list.head()
        println("before reverse: $head")
        LinkedListUtils.printList(head)

        val node = No206ReverseLinkedList.recursivelyReverse(list.head())
        assertEquals(4, node?.value)
        println("after reverse: $node")
        LinkedListUtils.printList(node)
    }
}
