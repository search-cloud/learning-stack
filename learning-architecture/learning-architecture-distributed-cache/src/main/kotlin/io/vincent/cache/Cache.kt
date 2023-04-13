package io.vincent.cache

import java.io.Serializable

/**
 * Cache.
 *
 * @author Vincent
 * @since 1.0, 1/22/19
 */
interface Cache<K : Serializable, V : Serializable> {

    /**
     * Add value to cache.
     *
     * @param key io.vincent.cache.lru.getKey of value.
     * @param value value to add.
     * @param periodMillis time.
     */
    fun add(key: K, value: V?, periodMillis: Long)

    /**
     * Add value to cache.
     *
     * @param key io.vincent.cache.lru.getKey of value.
     * @param value value to add.
     */
    fun add(key: K, value: V?)

    /**
     * Remove object by io.vincent.cache.lru.getKey.
     *
     * @param key io.vincent.cache.lru.getKey of value.
     */
    fun remove(key: K)

    /**
     * Get value by io.vincent.cache.lru.getKey.
     *
     * @param key io.vincent.cache.lru.getKey of value.
     * @return value.
     */
    fun get(key: K): V?

    /**
     * Clear all cache data.
     */
    fun clear()

    /**
     * Return the size of the cache.
     *
     * @return size.
     */
    fun size(): Long

    fun periodMillis(): Long {
        return defaultExpiryTime
    }

    // default expiry time 30 seconds.
    val defaultExpiryTime: Long
        get() = 30 * 1000
}
