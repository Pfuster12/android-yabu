package com.android.yabu.repositories.feed.source.network

/**
 * API response for a query on a list of random titles.
 */
data class TitlesQueryResponse(
    val query: Query
)

data class Query(
    val random: List<RandomTitle>
)

data class RandomTitle(
    val id: Int,
    val ns: Int,
    val title: String
)