package com.android.yabu.repositories

/**
 * A resource fetched from a data source.
 * Follows a step by step logic to fetch from disk and network sources.
 * See https://developer.android.com/topic/libraries/architecture/images/network-bound-resource.png
 */
abstract class NetworkResource<T> {

    abstract fun loadFromDisk(): T

    abstract fun shouldFetch(): Boolean

    abstract fun fetchData()

    abstract fun fetchFail()

    abstract fun processResponse(): T

    abstract fun saveToDisk(feed: T)
}