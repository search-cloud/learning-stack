package io.vincent.cache

import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * @author Vincent
 * @since 1.0, 1/22/19
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SimpleInMemoryCacheTest {

    private val simpleInMemoryCache = SimpleInMemoryCache<String, String>()
    private val key = "iPhone6"
    private val value = "iPhone6 64G"

    @Test
    fun add() {
        simpleInMemoryCache.add(key, "iPhone6 64G")

        Thread.sleep(30 * 1000)

        val actual = simpleInMemoryCache.get(key)
        assertEquals(value, actual, "cache is not found of exp.")
        println(actual)
    }

    @Test
    fun remove() {
    }

    @Test
    fun get() {
        val actual1 = simpleInMemoryCache.get(key)
        assertEquals(value, actual1, "cache is not found of exp.")
        println(actual1)

        Thread.sleep(370 * 1000)
        assertNull(actual1, "cache also active!")
    }

    @Test
    fun clear() {
    }

    @Test
    fun size() {
    }
}
