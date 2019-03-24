package io.vincent.learning.stack.algorithm.leetcode

import io.vincent.learning.stack.algorithm.linked.SinglyLinkedList
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by Vincent on 2019/3/24.
 *
 * @author Vincent
 * @since 1.0, 2019/3/24
 */
class ReverseLinkedListTest {

    @Test
    fun reverse() {
        val list = SinglyLinkedList<Int>()

        for (i in 0..10) {
            list.add(i)
        }

        for (i in 0 until list.size) {
            println(list.get(i))
        }

        var node = ReverseLinkedList.reverse(list.head())
        assertEquals(10, node?.data)
        println("after reverse: $node")
        while (node?.next != null) {
            println(node.data)
            node = node.next
        }
    }
}
