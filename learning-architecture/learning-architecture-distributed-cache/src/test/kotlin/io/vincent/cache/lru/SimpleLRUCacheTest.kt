package io.vincent.cache.lru

import org.junit.Test

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

        println(lruCache.get(1))
        println(lruCache.get(10))
        println(lruCache.get(15))
        println(lruCache.get(12))
        println(lruCache.get(18))
        println(lruCache.get(13))

    }
}
