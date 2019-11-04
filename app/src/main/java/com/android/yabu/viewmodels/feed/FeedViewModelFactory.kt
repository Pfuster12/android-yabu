package com.android.yabu.viewmodels.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.yabu.repositories.feed.FeedRepository

/**
 * [ViewModelProvider.Factory] implementation to pass the [FeedRepository] into the ViewModel.
 */
class FeedViewModelFactory(private val feedRepository: FeedRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(FeedRepository::class.java)
            .newInstance(feedRepository)
    }
}