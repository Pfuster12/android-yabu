package com.android.yabu.repositories.feed.source.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit Wikipedia Webservice.
 */
interface WikipediaWebservice {

    @GET
    fun getRandomTitles(@Query("action") action: String = "query",
                        @Query("list") list: String = "random",
                        @Query("rnnamespace") rnnamespace: Int = 0,
                        @Query("rnlimit") rnlimit: Int = 11,
                        @Query("format") format: String = "json"): Call<TitlesQueryResponse>

    @GET
    fun getExtract(@Query("action") action: String = "query",
                   @Query("prop") prop: String = "extracts",
                   @Query("explaintext") explaintext: Boolean = true,
                   @Query("exsectionformat") exsectionformat: String = "plain",
                   @Query("format") format: String = "json",
                   @Query("formatversion") formatVersion: Int = 2,
                   @Query("title", encoded = true) title: String): Call<ExtractsQueryResponse>
}