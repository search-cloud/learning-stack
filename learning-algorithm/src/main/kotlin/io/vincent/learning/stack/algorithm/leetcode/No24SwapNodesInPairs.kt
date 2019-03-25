package io.vincent.learning.stack.algorithm.leetcode

import io.vincent.learning.stack.algorithm.leetcode.linked.ListNode

/**
 * Swap Nodes in Pairs.
 * </p>
 * Given a linked list, swap every two adjacent nodes and return its head.
 * </p>
 * You may not modify the values in the list's nodes, only nodes itself may be changed.
 * </p>
 * Example:
 * </p>
 *  Given 1->2->3->4, you should return the list as 2->1->4->3.
 *
 * @author Vincent
 * @since 1.0, 2019/3/24
 */
object No24SwapNodesInPairs {

    fun swapPairs(head: ListNode?): ListNode? {
        if (head?.next == null) {
            return head
        }

        var p1 = head
        var p2 = head.next

        val newHead = p2

        while (p1 != null && p2 != null) {
            val prev: ListNode? = p1
            val temp: ListNode? = p2.next

            p2.next = p1
            p1.next = temp

            p1 = temp
            p2 = temp?.next
            if (p2 != null) {
                prev?.next = p2
            } else {
                prev?.next = p1
            }

        }

        return newHead
    }

    fun swapPairs1(head: ListNode?): ListNode? {
        if (head?.next == null) {
            return head
        }

        var pre: ListNode? = ListNode(-1)
        pre?.next = head
        val newHead = head.next

        while(pre?.next != null && pre?.next?.next != null) {
            val p1 = pre.next
            val p2 = p1?.next

            p1?.next = p2?.next
            p2?.next = p1
            pre.next = p2

            pre = p1
        }

        return newHead
    }
}
