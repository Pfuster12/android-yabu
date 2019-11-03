package com.android.yabu.repositories

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.*

/**
 * A generic resource [T] that can provide a resource backed by both the disk and the network.
 * Network source may have a different response [K] processed into [T].
 * Follows a step by step logic to fetch from disk or network source.
 * See https://developer.android.com/topic/libraries/architecture/images/network-bound-resource.png
 */
abstract class NetworkResource<T, K>(private val viewModelScope: CoroutineScope) {

    /**
     * A [MediatorLiveData] resource.
     */
    private val result = MediatorLiveData<Resource<T>>()

    @WorkerThread
    abstract suspend fun loadFromDisk(): LiveData<T>

    @MainThread
    abstract fun shouldFetch(diskResponse: T?): Boolean

    @WorkerThread
    abstract suspend fun fetchData(): Response<K>

    @MainThread
    abstract fun processResponse(response: K): T

    @WorkerThread
    abstract suspend fun saveToDisk(data: T): Boolean

    fun launch() {
        viewModelScope.launch {
            val diskSource = loadFromDisk()

            if (shouldFetch(diskSource.value)) {
                // re-attach the disk source and dispatch a loading value,
                result.addSource(diskSource) { newData ->
                    setValue(Resource.loading(newData))
                }

                // start network fetch,
                val fetchTask = async(Dispatchers.IO) { fetchData() }
                when (val response = fetchTask.await()) {
                    is Success -> {
                        // save new data to disk and dispatch fresh disk value,
                        withContext(Dispatchers.IO) {
                            saveToDisk(processResponse(response.data))
                        }

                        result.addSource(loadFromDisk()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }

                    is Failure -> {
                        // re-use the disk data and send the error response,
                        result.addSource(diskSource) { newData ->
                            Resource.error(response.message, newData)
                        }
                    }
                }
            } else {
                // re-use disk source,
                result.addSource(diskSource) { data ->
                    setValue(Resource.success(data))
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<T>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    fun asLiveData(): LiveData<Resource<T>> = result
}