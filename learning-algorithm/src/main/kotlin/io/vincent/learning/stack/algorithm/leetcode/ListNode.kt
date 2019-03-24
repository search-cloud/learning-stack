package io.vincent.learning.stack.algorithm.leetcode

/**
 * Created by Vincent on 2019/3/24.
 * @author Vincent
 * @since 1.0, 2019/3/24
 */
class ListNode(var value: Int, var next: ListNode? = null) {
    override fun toString(): String {
        return "ListNode{" +
                "value=" + value +
                ", next=" + next +
                '}'.toString()
    }
}
