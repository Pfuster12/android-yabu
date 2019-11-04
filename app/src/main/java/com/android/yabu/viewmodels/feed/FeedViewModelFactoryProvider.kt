package com.android.yabu.viewmodels.feed

import android.content.Context
import com.android.yabu.repositories.feed.FeedRepository
import com.android.yabu.repositories.feed.source.FeedCache
import com.android.yabu.repositories.feed.source.network.WikipediaWebservice
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Provides singleton dependencies to [FeedViewModelFactory].
 */
object FeedViewModelFactoryProvider {

    fun create(context: Context?): FeedViewModelFactory {
        return FeedViewModelFactory(provideRepository(context))
    }

    private fun provideRepository(context: Context?): FeedRepository {
        return FeedRepository.getInstance(provideWebservice(),
            provideCache(context))
    }

    private fun provideWebservice(): WikipediaWebservice {
        val retrofit = Retrofit.Builder()
            .baseUrl(WikipediaWebservice.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create<WikipediaWebservice>(WikipediaWebservice::class.java)
    }

    private fun provideCache(context: Context?): FeedCache {
        return FeedCache(context)
    }
}