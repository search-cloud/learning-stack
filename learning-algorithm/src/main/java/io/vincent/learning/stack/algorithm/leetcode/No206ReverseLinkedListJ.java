package io.vincent.learning.stack.algorithm.leetcode;

import io.vincent.learning.stack.algorithm.leetcode.linked.ListNode;

/**
 * Created by Vincent on 2019/3/25.
 *
 * @author Vincent
 * @since 1.0, 2019/3/25
 */
public class No206ReverseLinkedListJ {

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;

        // get second node
        ListNode second = head.next;
        // set first's next to be null
        head.next = null;

        ListNode rest = reverseList(second);
        second.next = head;

        return rest;
    }
}
