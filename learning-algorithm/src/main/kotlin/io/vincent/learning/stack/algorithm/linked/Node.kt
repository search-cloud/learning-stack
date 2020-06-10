package io.vincent.learning.stack.algorithm.linked

/**
 * Singly Linked list Node.
 *
 * @author Vincent
 * @since 1.0, 2019/3/23
 */
class Node<T>(var data: T?, var next: Node<T>?) {

    override fun toString(): String {
        return "SinglyNode{" +
                "data=" + data +
                ", next=" + next +
                '}'.toString()
    }
}
