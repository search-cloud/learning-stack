package io.vincent.learning.stack.algorithm.leetcode

import io.vincent.learning.stack.algorithm.linked.Node
import org.jetbrains.annotations.Contract

/**
 * Reverse linked List.
 * </p>
 * https://leetcode.com/problems/reverse-linked-list/
 *
 * @author Vincent
 * @since 1.0, 2019/3/23
 */
object No206ReverseLinkedList {

    @Contract("null -> null")
    fun <T> reverse(head: Node<T>?): Node<T>? {
        if (head?.next == null) {
            return head
        }

        // 记录前驱节点, Default null
        var prev: Node<T>? = null
        // 记录当前节点
        var curr = head

        while (curr != null) {
            val nextTemp = curr.next
            curr.next = prev
            prev = curr
            curr = nextTemp
        }
        return prev
    }
}
