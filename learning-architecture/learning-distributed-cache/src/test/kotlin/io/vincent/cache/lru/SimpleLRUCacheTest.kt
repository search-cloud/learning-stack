package io.vincent.cache.lru

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Least Recently Used Cache Test.
 *
 * @author Vincent
 * @since 1.0, 1/24/19
 */
class SimpleLRUCacheTest {

    @Test
    fun add() {
        val lruCache = SimpleLRUCache<Int, Int>()

        lruCache.add(1, 1)
        lruCache.add(10, 10)
        lruCache.add(15, 15)
        lruCache.add(10, 10)
        lruCache.add(12, 12)
        lruCache.add(18, 18)
        lruCache.add(13, 13)

        val value0 = lruCache.get(1)
        val value1 = lruCache.get(10)
        val value2 = lruCache.get(15)
        val value3 = lruCache.get(12)
        val value4 = lruCache.get(18)
        val value5 = lruCache.get(13)

        println(value0)
        println(value1)
        println(value2)
        println(value3)
        println(value4)
        println(value5)

        assertEquals(null, value0)
        assertEquals(10, value1)
        assertEquals(null, value2)
        assertEquals(12, value3)
        assertEquals(18, value4)
        assertEquals(13, value5)

    }
}
