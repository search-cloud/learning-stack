package io.vincent.learning.stack.algorithm.leetcode

import io.vincent.learning.stack.algorithm.leetcode.linked.LinkedListUtils.buildLinkedList
import io.vincent.learning.stack.algorithm.leetcode.linked.LinkedListUtils.printList
import org.junit.Test

/**
 * Created by Vincent on 2019/3/24.
 *
 * @author Vincent
 * @since 1.0, 2019/3/24
 */
class No24SwapNodesInPairsTest {

    @Test
    fun swapPairs() {
        val list = buildLinkedList(4)

        val head = list.head()
        println("before reverse: $head")
        printList(head)

        val node = No24SwapNodesInPairs.swapPairs(list.head())
        println("after reverse: $node")
        printList(node)
    }

    @Test
    fun swapPairs1() {
        val list = buildLinkedList(8)

        val head = list.head()
        println("before reverse: $head")
        printList(head)

        val node = No24SwapNodesInPairs.swapPairs(list.head())
        println("after reverse: $node")
        printList(node)
    }

    @Test
    fun swapPairs2() {
        val list = buildLinkedList(3)

        val head = list.head()
        println("before reverse: $head")
        printList(head)

        val node = No24SwapNodesInPairs.swapPairs(list.head())
        println("after reverse: $node")
        printList(node)
    }

    @Test
    fun swapPairs22() {
        val list = buildLinkedList(3)

        val head = list.head()
        println("before reverse: $head")
        printList(head)

        val node = No24SwapNodesInPairs.swapPairs1(list.head())
        println("after reverse: $node")
        printList(node)
    }

    @Test
    fun swapPairs23() {
        val list = buildLinkedList(8)

        val head = list.head()
        println("before reverse: $head")
        printList(head)

        val node = No24SwapNodesInPairs.swapPairs1(list.head())
        println("after reverse: $node")
        printList(node)
    }

}
