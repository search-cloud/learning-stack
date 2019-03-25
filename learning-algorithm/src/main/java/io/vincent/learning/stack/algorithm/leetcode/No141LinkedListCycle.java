package io.vincent.learning.stack.algorithm.leetcode;

import io.vincent.learning.stack.algorithm.leetcode.linked.ListNode;
import org.jetbrains.annotations.Contract;

import java.util.HashSet;
import java.util.Set;

/**
 * 141. Linked List Cycle
 * Easy
 *
 * Given a linked list, determine if it has a cycle in it.
 *
 * To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.
 *
 * Example 1:
 *
 * Input: head = [3,2,0,-4], pos = 1
 * Output: true
 * Explanation: There is a cycle in the linked list, where tail connects to the second node.
 *  3 -> 2 -> 0 -> -4
 *       ^         /
 *        \--------
 *
 *  ``````       ``````       ```````       ```````
 * `  3   `---->`  2   `---->`   0   `---->`   4   `
 * `  `   `     `  `   `     `   `   `     `   `   `
 *  ``````       ``````       ```````       ```````
 *                 ^                            `
 *                  ``                         ``
 *                    ```                    ```
 *                        ``````````````````
 *
 * Example 2:
 *
 * Input: head = [1,2], pos = 0
 * Output: true
 * Explanation: There is a cycle in the linked list, where tail connects to the first node.
 *
 *
 * Example 3:
 *
 * Input: head = [1], pos = -1
 * Output: false
 * Explanation: There is no cycle in the linked list.
 *
 *
 *
 *
 * Follow up:
 *
 * Can you solve it using O(1) (i.e. constant) memory?
 *
 * @author Vincent
 * @since 1.0, 2019/3/24
 */
public class No141LinkedListCycle {

    @Contract("null -> false")
    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        Set<ListNode> s = new HashSet<>();
        ListNode curr = head;
        while (curr != null && !s.contains(curr.next)) {
            s.add(curr.next);
            curr = curr.next;
        }
        return curr != null;
    }
}
