package com.android.yabu.repositories

/**
 * A generic class that contains data and status about loading this data.
 */
sealed class Response<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Response<T>(data)
    class Loading<T>(data: T? = null) : Response<T>(data)
    class Error<T>(code: Int, message: String, data: T? = null) : Response<T>(data, message)
}
