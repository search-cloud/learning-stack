package io.vincent.learning.stack.algorithm.leetcode;

import io.vincent.learning.stack.algorithm.leetcode.linked.LinkedList;
import io.vincent.learning.stack.algorithm.leetcode.linked.LinkedListUtils;
import io.vincent.learning.stack.algorithm.leetcode.linked.ListNode;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Vincent on 2019/3/24.
 *
 * @author Vincent
 * @since 1.0, 2019/3/24
 */
public class No141LinkedListCycleJTest {

    @Test
    public void hasCycle() {
        LinkedList list = buildLinkedListCycle(4, 3);
        boolean hasCycle = No141LinkedListCycle.hasCycle(list.head());
        System.out.println("has cycle?: " + hasCycle);
        assertTrue(hasCycle);
    }

    @Test
    public void hasCycle1() {
        LinkedList list = LinkedListUtils.buildLinkedList(4);
        boolean hasCycle = No141LinkedListCycle.hasCycle(list.head());
        System.out.println("has cycle?: " + hasCycle);
        assertTrue(hasCycle);
    }

    private LinkedList buildLinkedListCycle(int n, int index) {
        LinkedList list = new LinkedList();

        if (index > n) {
            return list;
        }

        ListNode temp = null;
        for (int i = 1; i <= n; i++) {
            ListNode node = new ListNode(i);
            if (i == index) {
                temp = node;
            }
            list.add(node);
        }
        if (list.tail() != null) {
            list.tail().next = temp;
        }

        return list;
    }
}
