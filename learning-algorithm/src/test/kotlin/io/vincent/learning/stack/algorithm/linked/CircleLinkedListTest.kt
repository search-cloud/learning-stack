package io.vincent.learning.stack.algorithm.linked

import org.junit.Test

/**
 * Created by Vincent on 1/24/19.
 *
 * @author Vincent
 * @since 1.0, 1/24/19
 */
class CircleLinkedListTest {

    @Test
    fun circleLinkedListTest() {
        val list = CircleLinkedList<Int>()

        for (i in 0..10) {
            list.add(i)
        }

        val index = 0
        var current = list.get(index)
        // This loop will never stop.
        while (current != null) {
            println(current)
            current = list.next(current)
        }
    }

}
