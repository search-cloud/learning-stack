package io.vincent.learning.stack.algorithm.leetcode;

/**
 * Created by Vincent on 2019/3/24.
 *
 * @author Vincent
 * @since 1.0, 2019/3/24
 */
public class No24SwapNodesInPairsJ {
    public ListNode swapPairs1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode newHead = head.next;

        while(pre != null && pre.next != null && pre.next.next != null) {
            ListNode p1 = pre.next;
            ListNode p2 = p1.next;
            p1.next = p2.next;
            p2.next = p1;
            pre.next = p2;
            pre = p1;
        }

        return newHead;
    }
}
