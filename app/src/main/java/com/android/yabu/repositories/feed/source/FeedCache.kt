package com.android.yabu.repositories.feed.source

import android.content.Context
import com.android.yabu.repositories.SimpleCache
import com.android.yabu.repositories.feed.Feed
import com.android.yabu.utils.FileUtils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Cache for the Wikipedia feed data.
 */
class FeedCache(val context: Context?) : SimpleCache<Feed> {

    companion object {
        const val FEED_CACHE_FILE_NAME = "yabu-feed-cache.txt"
    }

    override suspend fun writeCache(data: Feed): Boolean {
        val str = Gson().toJson(data)
        return withContext(Dispatchers.IO) {
            FileUtils.writeFile(context, FEED_CACHE_FILE_NAME, str)
        }
    }

    override suspend fun readCache(): Feed? {
        val str = withContext(Dispatchers.IO) { FileUtils.readFile(context, FEED_CACHE_FILE_NAME) }

        return try {
            Gson().fromJson(str, Feed::class.java)
        } catch (e: Exception) {
            null
        }
    }
}