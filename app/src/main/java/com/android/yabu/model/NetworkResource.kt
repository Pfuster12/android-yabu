package com.android.yabu.model

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.android.yabu.utils.LogUtils
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

    init {
        launch()
    }

    @WorkerThread
    abstract suspend fun loadFromDisk(): LiveData<T?>

    @MainThread
    abstract fun shouldFetch(diskResponse: T?): Boolean

    @WorkerThread
    abstract suspend fun fetchData(): Response<K>

    @MainThread
    abstract fun processResponse(response: K): T

    @WorkerThread
    abstract suspend fun saveToDisk(data: T): Boolean

    private fun launch() {
        viewModelScope.launch {
            val diskSource = loadFromDisk()

            if (shouldFetch(diskSource.value)) {
                LogUtils.debug("Disk data is invalid. Fetching from network...")

                // re-attach the disk source and dispatch a loading value,
                result.addSource(diskSource) { newData ->
                    setValue(Resource.loading(newData))
                }

                // remove the source before the fetch as two sources can't be duplicated
                // in case of a failure,
                result.removeSource(diskSource)

                // start network fetch,
                val fetchTask = async(Dispatchers.IO) { fetchData() }
                when (val response = fetchTask.await()) {
                    is Success -> {
                        // save new data to disk and dispatch fresh disk value,
                        withContext(Dispatchers.IO) {
                            saveToDisk(processResponse(response.data))
                        }

                        // add new disk source and send success,
                        result.addSource(loadFromDisk()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }

                    is Failure -> {
                        // re-use the disk data and send the error response,
                        result.addSource(diskSource) { newData ->
                            setValue(Resource.error(response.message, newData))
                        }
                    }
                }
            } else {
                LogUtils.debug("Disk data is valid. Returning disk source data...")

                // re-use disk source and send a success value,
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