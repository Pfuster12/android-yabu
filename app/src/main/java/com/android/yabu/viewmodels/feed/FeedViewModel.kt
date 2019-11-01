package com.android.yabu.viewmodels.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.yabu.repositories.feed.Feed
import com.android.yabu.repositories.feed.FeedRepository
import javax.inject.Inject

/**
 * ViewModel for the feed data UI handler.
 */
class FeedViewModel @Inject constructor(feedRepository: FeedRepository) : ViewModel() {
    val feed: LiveData<Feed> = feedRepository.fetchFeed()
}