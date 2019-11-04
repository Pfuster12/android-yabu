package com.android.yabu.utils

import android.util.Log
import com.android.yabu.BuildConfig

/**
 * Debug only Log for different levels.
 */
object LogUtils {

    private const val LOG_TAG = "Yabu-"

    fun debug(msg: String) {
        if (BuildConfig.DEBUG)
            Log.d(LOG_TAG, msg)
    }

    fun warn(msg: String) {
        if (BuildConfig.DEBUG)
            Log.w(LOG_TAG, msg)
    }
}