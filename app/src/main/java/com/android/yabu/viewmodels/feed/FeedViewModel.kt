package com.android.yabu.viewmodels.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.yabu.repositories.Resource
import com.android.yabu.repositories.Response
import com.android.yabu.repositories.feed.Feed
import com.android.yabu.repositories.feed.FeedRepository

/**
 * ViewModel for a [Feed] [Response] UI handlers.
 */
class FeedViewModel constructor(feedRepository: FeedRepository) : ViewModel() {
    val feed: LiveData<Resource<Feed>> = feedRepository.fetchFeed(viewModelScope)
}