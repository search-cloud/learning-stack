package io.vincent.cache

import java.io.Serializable
import java.lang.ref.SoftReference
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.DelayQueue
import java.util.concurrent.Delayed
import java.util.concurrent.TimeUnit

/**
 * Simple memory cache implementation with delay queue.
 *
 * @author Vincent.
 * @since 1.0, 1/22/19.
 */
class InMemoryCacheWithDelayQueue<K : Serializable, V : Serializable> : Cache<K, V> {

    private val cache = ConcurrentHashMap<K, SoftReference<V>>()
    private val cleaningUpQueue = DelayQueue<DelayedCacheData>()

    init {
        val cleanerThread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    val delayedCacheData = cleaningUpQueue.take()
                    cache.remove(delayedCacheData.key, delayedCacheData.reference)
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }

            }
        }
        cleanerThread.isDaemon = true
        cleanerThread.start()
    }

    override fun add(key: K, value: V?, periodMillis: Long) {
        if (value == null) {
            cache.remove(key)
        } else {
            val expiryTime = System.currentTimeMillis() + periodMillis
            val reference = SoftReference(value)
            cache[key] = reference
            cleaningUpQueue.put(DelayedCacheData(key, reference, expiryTime))
        }
    }

    override fun add(key: K, value: V?) {
        add(key, value, periodMillis())
    }

    override fun remove(key: K) {
        cache.remove(key)
    }

    override operator fun get(key: K): V? {
        return Optional.ofNullable(cache[key]).map<V> { it.get() }.orElse(null)
    }

    override fun clear() {
        cache.clear()
    }

    override fun size(): Long {
        return cache.size.toLong()
    }

    private inner class DelayedCacheData(val key: K, val reference: SoftReference<V>, private val expiryTime: Long) : Delayed {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || javaClass != other.javaClass) return false
            val that = other as InMemoryCacheWithDelayQueue<*, *>.DelayedCacheData
            return expiryTime == that.expiryTime && key == that.key && reference == that.reference
        }

        override fun hashCode(): Int {
            return Objects.hash(key, reference, expiryTime)
        }

        override fun getDelay(unit: TimeUnit): Long {
            return unit.convert(expiryTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
        }

        override fun compareTo(other: Delayed): Int {
            return java.lang.Long.compare(expiryTime, (other as InMemoryCacheWithDelayQueue<*, *>.DelayedCacheData).expiryTime)
        }
    }
}
