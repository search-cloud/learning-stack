package io.vincent.learning.stack.algorithm.leetcode;

import io.vincent.learning.stack.algorithm.leetcode.linked.LinkedList;
import io.vincent.learning.stack.algorithm.leetcode.linked.LinkedListUtils;
import io.vincent.learning.stack.algorithm.leetcode.linked.ListNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(hasCycle).isTrue();
    }

    @Test
    public void hasCycle0() {
        LinkedList list = LinkedListUtils.buildLinkedList(4);
        boolean hasCycle = No141LinkedListCycle.hasCycle(list.head());
        System.out.println("has cycle?: " + hasCycle);
        assertThat(hasCycle).isFalse();
    }

    @Test
    public void hasCycle1() {
        LinkedList list = LinkedListUtils.buildLinkedList(4);
        boolean hasCycle = No141LinkedListCycle.hasCycle1(list.head());
        System.out.println("has cycle?: " + hasCycle);
        assertThat(hasCycle).isFalse();
    }

    @Test
    public void hasCycle11() {
        LinkedList list = buildLinkedListCycle(4, 3);
        boolean hasCycle = No141LinkedListCycle.hasCycle1(list.head());
        System.out.println("has cycle?: " + hasCycle);
        assertThat(hasCycle).isTrue();
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
