package com.android.yabu.model

import androidx.annotation.WorkerThread

/**
 * A simple generic cache that writes and reads a JSON of the object instance.
 * Read operation parses into type [T] wrapped in a [Response].
 */
interface SimpleCache<T> {

    /**
     * Cache data.
     * @return true if successfully cached.
     */
    @WorkerThread
    suspend fun writeCache(data: T): Boolean

    /**
     * Read cache data.
     * @return [T]
     */
    @WorkerThread
    suspend fun readCache(): T?
}