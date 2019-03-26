package io.vincent.learning.stack.algorithm.leetcode;

import io.vincent.learning.stack.algorithm.leetcode.linked.ListNode;
import org.jetbrains.annotations.Contract;

import java.util.HashSet;
import java.util.Set;

/**
 * 141. Linked List Cycle
 * Easy
 * <p>
 * Given a linked list, determine if it has a cycle in it.
 * <p>
 * To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.
 * <p>
 * Example 1:
 * <p>
 * Input: head = [3,2,0,-4], pos = 1
 * Output: true
 * Explanation: There is a cycle in the linked list, where tail connects to the second node.
 * 3 -> 2 -> 0 -> -4
 * ^         /
 * \--------
 * <p>
 * ``````       ``````       ```````       ```````
 * `  3   `---->`  2   `---->`   0   `---->`   4   `
 * `  `   `     `  `   `     `   `   `     `   `   `
 * ``````       ``````       ```````       ```````
 * ^                            `
 * ``                         ``
 * ```                    ```
 * ``````````````````
 * <p>
 * Example 2:
 * <p>
 * Input: head = [1,2], pos = 0
 * Output: true
 * Explanation: There is a cycle in the linked list, where tail connects to the first node.
 * <p>
 * <p>
 * Example 3:
 * <p>
 * Input: head = [1], pos = -1
 * Output: false
 * Explanation: There is no cycle in the linked list.
 * <p>
 * <p>
 * <p>
 * <p>
 * Follow up:
 * <p>
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

    @Contract(value = "null -> false", pure = true)
    public static boolean hasCycle1(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        ListNode slow = head, fast = head.next;
        while (slow != fast && slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow == fast;
    }
}
