package io.vincent.learning.stack.algorithm.leetcode

import io.vincent.learning.stack.algorithm.linked.SinglyLinkedList
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
        val list = SinglyLinkedList<Int>()

        for (i in 0..10) {
            list.add(i)
        }

        for (i in 0 until list.size) {
            println(list.get(i))
        }

        var node = No206ReverseLinkedList.reverse(list.head())
        assertEquals(10, node?.data)
        println("after reverse: $node")
        while (node?.next != null) {
            println(node.data)
            node = node.next
        }
    }
}
