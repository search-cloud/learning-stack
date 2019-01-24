package io.vincent.cache

import java.io.Serializable
import java.lang.ref.SoftReference
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Simple memory cache implementation.
 *
 * @author Vincent.
 * @since 1.0, 1/22/19.
 */
class SimpleInMemoryCache<K : Serializable, V : Serializable> : Cache<K, V> {

    private val cache = ConcurrentHashMap<K, SoftReference<CacheData<V>>>()

    init {
        // 初始化清理线程
        val cleanerThread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    Thread.sleep((CLEAN_UP_PERIOD_IN_SEC * 1000).toLong())
                    cache.entries.removeIf { entry ->
                        Optional.ofNullable(entry.value)
                                .map<CacheData<V>> { it.get() }
                                .map { it.expired }.orElse(false)
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
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
            val time = System.currentTimeMillis() + periodMillis
            cache[key] = SoftReference(CacheData(value, time))
        }
    }

    override fun add(key: K, value: V?) {
        this.add(key, value, periodMillis())
    }

    override fun remove(key: K) {
        cache.remove(key)
    }

    override operator fun get(key: K): V? {
        return Optional.ofNullable(cache[key])
                .map<CacheData<V>> { it.get() }
                .filter { vCacheData -> !vCacheData.expired }
                .map<V> { it.value }.orElse(null)
    }

    override fun clear() {
        cache.clear()
    }

    override fun size(): Long {
        return cache.entries.stream()
                .filter { it ->
                    Optional.ofNullable(it.value)
                            .map<CacheData<V>> { it.get() }
                            .map { cacheObject -> !cacheObject.expired }
                            .orElse(false)
                }.count()
    }

    private inner class CacheData<V>(val value: V, val expiryTime: Long) {
        internal val expired: Boolean
            get() = System.currentTimeMillis() > expiryTime
    }

    companion object {
        // clean up period second.
        private const val CLEAN_UP_PERIOD_IN_SEC = 5
    }

    // default expiry time 1 minutes.
    override val defaultExpiryTime: Long = 360 * 1000
}
