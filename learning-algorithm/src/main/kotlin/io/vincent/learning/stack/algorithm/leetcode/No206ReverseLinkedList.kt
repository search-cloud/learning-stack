package io.vincent.learning.stack.algorithm.leetcode

import io.vincent.learning.stack.algorithm.leetcode.linked.ListNode
import org.jetbrains.annotations.Contract

/**
 * Reverse linked List. (Iteratively)
 * </p>
 * https://leetcode.com/problems/reverse-linked-list/
 *
 * @author Vincent
 * @since 1.0, 2019/3/23
 */
object No206ReverseLinkedList {

    /**
     * Approach #1 (Iterative)
     *    Assume that we have linked list 1 → 2 → 3 → Ø, we would like to change it to Ø ← 1 ← 2 ← 3.
     *
     *    While you are traversing the list, change the current node's next pointer to point to its previous element. Since a node does not have reference to its previous node, you must store its previous element beforehand. You also need another pointer to store the next node before changing the reference. Do not forget to return the new head reference at the end!
     *
     *    Complexity analysis
     *
     *    Time complexity : O(n)O(n). Assume that nn is the list's length, the time complexity is O(n)O(n).
     *
     *    Space complexity : O(1)O(1).
     */
    @Contract("null -> null")
    fun reverse(head: ListNode?): ListNode? {
        if (head?.next == null) {
            return head
        }

        // 记录前驱节点, Default null
        var prev: ListNode? = null
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

    /**
     * Approach #2 (Recursive)
     *  The recursive version is slightly trickier and the key is to work backwards. Assume that the rest of the list had already been reversed, now how do I reverse the front part? Let's assume the list is: n1 → … → nk-1 → nk → nk+1 → … → nm → Ø
     *
     *  Assume from node nk+1 to nm had been reversed and you are at node nk.
     *
     *  n1 → … → nk-1 → nk → nk+1 ← … ← nm
     *
     *  We want nk+1’s next node to point to nk.
     *
     *  So,
     *
     *   nk.next.next = nk;
     *
     *   Be very careful that n1's next must point to Ø. If you forget about this, your linked list has a cycle in it. This bug could be caught if you test your code with a linked list of size 2.
     *
     *   Complexity analysis
     *
     *      Time complexity : O(n)O(n). Assume that nn is the list's length, the time complexity is O(n)O(n).
     *
     *      Space complexity : O(n)O(n). The extra space comes from implicit stack space due to recursion. The recursion could go up to nn levels deep.
     */
    @Contract("null -> null")
    fun recursivelyReverse(head: ListNode?): ListNode? {
        if (head?.next == null) {
            return head
        }
        val result = recursivelyReverse(head.next)
        head.next?.next = head
        head.next = null
        return result
    }
}
