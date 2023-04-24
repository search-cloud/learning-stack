package io.vincent.cache.lru

import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

/**
 * Least Recently Used Cache.
 *
 * @author Vincent
 * @since 1.0, 1/23/19
 */
class SimpleLRUCache<K : Serializable, V : Serializable>(var capacity: Int = 4) {

    /**
     * Cache Linked Node.
     *
     * @author Vincent
     * @since 1.0, 1/23/19
     */
    private inner class Node<K, V>(val key: K, var value: V,
                           var prev: Node<K, V>? = null,
                           var next: Node<K, V>? = null) {

        override fun hashCode(): Int {
            return Objects.hashCode(key) xor Objects.hashCode(value)
        }

        override fun equals(other: Any?): Boolean {
            if (other === this)
                return true
            if (other is SimpleLRUCache<*, *>.Node<*, *>) {
                if (key == other.key && value == other.value)
                    return true
            }
            return false
        }
    }

    // Hash for O(1)
    private var queueTable: HashMap<K, Node<K, V>> = HashMap()

    private var head: Node<K, V>? = null
    private var tail: Node<K, V>? = null

    /**
     * Add element to cache.
     */
    fun add(key: K, value: V) {
        /**
         * if contains the element, move the node to head.
         */
        if (queueTable.containsKey(key)) {
            val node = queueTable[key]
            if (node != null) {
                // set new value.
                node.value = value
                // move the node to head.
                // delete and readd.
                remove(node)
                add(node)
            }
        } else {
            /**
             * if the table do not contains the element.
             */
            val newNode = Node(key, value)
            // Queue table fulled, delete tail element, then add to head.
            if (queueTable.size >= capacity) {
                queueTable.remove(tail!!.key)
                remove(tail!!)
                add(newNode)
            } else {
                // Queue table don't full, add to head.
                add(newNode)
            }
            // Add new node to queue table.
            queueTable[key] = newNode
        }
    }

    /**
     * Get by key.
     */
    fun get(key: K): V? {
        if (queueTable.containsKey(key)) {
            val node = queueTable[key]
            if (node != null) {
                remove(node)
                add(node)
                return node.value
            }
            return null
        }
        return null
    }

    /**
     * Add node to head.
     */
    private fun add(node: Node<K, V>) {
        node.next = head
        node.prev = null
        if (head != null) {
            head!!.prev = node
        }
        head = node
        if (tail == null) {
            tail = head
        }
    }

    /**
     * Remove node.
     */
    private fun remove(node: Node<K, V>) {
        if (node.prev != null) {
            node.prev!!.next = node.next
        } else {
            head = node.next
        }

        if (node.next != null) {
            node.next!!.prev = node.prev
        } else {
            tail = node.prev
        }
    }

}
