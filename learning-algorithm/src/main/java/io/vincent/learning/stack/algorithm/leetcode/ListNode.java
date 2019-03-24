package io.vincent.learning.stack.algorithm.leetcode;

/**
 * leetcode ListNode class.
 *
 * @author Vincent
 * @since 1.0, 2019/3/24
 */
public class ListNode {
    int value;
    ListNode next;

    ListNode(int value) {
        this.value = value;
        this.next = null;
    }

    public ListNode(int value, ListNode next) {
        this.value = value;
        this.next = next;
    }

    public String toString() {
        return "ListNode{" +
                       "value=" + value +
                       ", next=" + next +
                       '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
