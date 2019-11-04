package com.android.yabu.viewmodel.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.yabu.model.Resource
import com.android.yabu.model.Response
import com.android.yabu.model.feed.model.Feed
import com.android.yabu.model.feed.FeedRepository

/**
 * ViewModel for a [Feed] [Response] UI handlers.
 */
class FeedViewModel constructor(feedRepository: FeedRepository) : ViewModel() {
    val feed: LiveData<Resource<Feed>> = feedRepository.fetchFeed(viewModelScope)
}