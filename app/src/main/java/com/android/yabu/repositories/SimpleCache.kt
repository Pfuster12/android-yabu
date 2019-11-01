package com.android.yabu.repositories

/**
 * A simple generic cache that writes and reads a JSON string of the object instance.
 */
interface SimpleCache<T> {

    /**
     * Cache data.
     * @return true if successful cache.
     */
    fun cache(data: T): Boolean

    /**
     * Read cache data.
     * @return [T]
     */
    fun readFromCache(): T
}