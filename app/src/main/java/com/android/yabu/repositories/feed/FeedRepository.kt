package com.android.yabu.repositories.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.yabu.repositories.feed.datasources.FeedCache
import com.android.yabu.repositories.feed.datasources.WikipediaWebservice
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for the Feed data.
 */
// Informs Dagger that this class should be constructed only once.
@Singleton
class FeedRepository @Inject constructor(
    private val webservice: WikipediaWebservice,
    private val cache: FeedCache) {

    /**
     * Fetch the latest valid feed.
     */
    fun fetchFeed(): LiveData<Feed> {
        TODO("Use NetworkResource to follow logical steps to fetch data from necessary source.")
        return MutableLiveData<Feed>()
    }
}