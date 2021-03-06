package com.android.yabu.model.feed.model

/**
 * Wikipedia [Article] feed.
 * @property articles
 * @property timestamp [String] timestamp in the form of
 */
data class Feed(var articles: List<Article> = listOf(),
                val timestamp: FeedTimestamp = FeedTimestamp()
)