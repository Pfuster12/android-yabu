package com.android.yabu.repositories.feed.source.network

/**
 * An API response class for a Wiki Extract.
 */
data class ExtractsQueryResponse(
    val query: ExtractsQuery?,
    val warnings: Warnings
)

data class ExtractsQuery(
    val pages: List<Page>
)

data class Page(
    val extract: String,
    val ns: Int,
    val pageid: Long,
    val title: String,
    val thumbnail: WikiThumbnail
)

data class WikiThumbnail(val source: String)

data class Warnings(
    val extracts: ExtractWarning
)

data class ExtractWarning (
    val warnings: String
)