package com.android.yabu.repositories.feed.datasources

import com.android.yabu.repositories.SimpleCache
import com.android.yabu.repositories.feed.Feed
import com.android.yabu.utils.FileUtils
import com.google.gson.Gson

/**
 * Cache for the Wikipedia feed data.
 */
class FeedCache : SimpleCache<Feed> {

    companion object {
        const val FEED_CACHE_FILE_NAME = "yabu-feed-cache.txt"
    }

    override fun cache(data: Feed): Boolean {
        return FileUtils.writeFile(FEED_CACHE_FILE_NAME)
    }

    override fun readFromCache(): Feed {
        val str = FileUtils.readFile(FEED_CACHE_FILE_NAME)

        return Gson().fromJson(str, Feed::class.java)
    }
}