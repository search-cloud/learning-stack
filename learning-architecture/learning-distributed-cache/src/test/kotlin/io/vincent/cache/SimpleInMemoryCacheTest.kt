package io.vincent.cache

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * @author Vincent
 * @since 1.0, 1/22/19
 */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SimpleInMemoryCacheTest {

    private val simpleInMemoryCache = SimpleInMemoryCache<String, String>()
    private val key = "iPhone6"
    private val value = "iPhone6 64G"
    private val key1 = "iPhone6s"
    private val value1 = "iPhone6s 128G"

    @BeforeTest
    fun init() {
        simpleInMemoryCache.add(key1, value1, 10 * 1000)
    }

    @Test
    fun add() {
        simpleInMemoryCache.add(key, value, 20 * 1000)

        Thread.sleep(15 * 1000)

        val actual = simpleInMemoryCache.get(key)
        assertEquals(value, actual, "cache is not found of exp.")
        println(actual)
    }

    @Test
    fun remove() {
    }

    @Test
    fun get() {
        val actual1 = simpleInMemoryCache.get(key1)
        assertEquals(value1, actual1, "cache is not found of exp.")
        println(actual1)
    }

    @Test
    fun getExp() {
        Thread.sleep(12 * 1000)
        val actual1 = simpleInMemoryCache.get(key1)
        assertNull(actual1, "cache also active!")
    }

    @Test
    fun clear() {
    }

    @Test
    fun size() {
    }
}
