package com.android.yabu.model

import androidx.annotation.WorkerThread

/**
 * Un-opinionated generic cache that writes and reads to persistent storage.
 * Read operation parses into type [T].
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