package io.vincent.cache

import java.util.*

/**
 * Create Simple Cache object with the help of HashMap...
 *
 * @author Vincent.
 */
open class CacheExample<K, T>(timeToLive: Long, timeInterval: Long, capacity: Int) {

    // time for data to live.
    private val timeToLive: Long = timeToLive * 1000
    private val cacheMap: HashMap<K, CacheObject> = HashMap(capacity)

    protected inner class CacheObject internal constructor(internal var value: T) {
        internal var lastAccessed = System.currentTimeMillis()
    }

    init {
        // daemon thread to listen
        if (timeToLive > 0 && timeInterval > 0) {
            val t = Thread {
                while (!Thread.currentThread().isInterrupted) {
                    try {
                        Thread.sleep(timeInterval * 1000)
                    } catch (ex: InterruptedException) {
                        ex.printStackTrace()
                        Thread.currentThread().interrupt()
                    }

                }
            }

            t.isDaemon = true
            t.start()
        }
    }

    /**
     * Put element.
     *
     * @param key   key of the element.
     * @param value the data need to cache.
     */
    fun put(key: K, value: T) {
        synchronized(cacheMap) {
            cacheMap.put(key, CacheObject(value))
        }
    }

    /**
     * Get the data by the key.
     *
     * @param key key of the element.
     * @return data.
     */
    operator fun get(key: K): T? {
        synchronized(cacheMap) {
            val c = cacheMap[key]

            return if (c == null)
                null
            else {
                // update the access time millis.
                c.lastAccessed = System.currentTimeMillis()
                c.value
            }
        }
    }

    /**
     * Remove element.
     *
     * @param key key of the element.
     */
    fun remove(key: K) {
        synchronized(cacheMap) {
            cacheMap.remove(key)
        }
    }

    /**
     * Get cache objects size.
     *
     * @return size of the cache.
     */
    fun size(): Int {
        synchronized(cacheMap) {
            return cacheMap.size
        }
    }

    /**
     * cleanup.
     */
    fun cleanup() {
        val now = System.currentTimeMillis()
        val deleteKey: ArrayList<K>

        synchronized(cacheMap) {
            val iterator = cacheMap.entries.iterator()

            deleteKey = ArrayList(cacheMap.size / 2 + 1)
            var c: CacheObject?

            while (iterator.hasNext()) {
                val next = iterator.next()
                c = next.value
                if (now > timeToLive + c.lastAccessed) {
                    deleteKey.add(next.key)
                }
            }
        }

        for (key in deleteKey) {
            synchronized(cacheMap) {
                cacheMap.remove(key)
            }
            Thread.yield()
        }
    }
}
