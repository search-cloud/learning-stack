package io.vincent.learning.stack.algorithm.linked

import kotlin.test.Test
import kotlin.test.assertFailsWith

/**
 * Created by Vincent on 1/23/19.
 *
 * @author Vincent
 * @since 1.0, 1/23/19
 */
class SinglyLinkedListTest {

    @Test
    fun testLinkedList() {
        val list = SinglyLinkedList<Int>()

        for (i in 0..10) {
            list.add(i)
        }

        for (i in 0 until list.size) {
            println(list.get(i))
        }

        assertFailsWith(IndexOutOfBoundsException::class, "IndexOutOfBoundsException") {
            list.get(22)
        }
    }

    @Test
    fun testLinkedListRemove() {
        val list = SinglyLinkedList<Int>()
        val expected = SinglyLinkedList<Int>()

        for (i in 0..10) {
            list.add(i)
            if (i % 2 != 0)
                expected.add(i)
        }

        for (i in 0 until list.size/2) if (i % 2 == 0)
            list.remove(i)

        println(list)
    }

}
