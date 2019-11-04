package com.android.yabu.repositories.feed.source.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit Wikipedia Webservice.
 */
interface WikipediaWebservice {

    companion object {
        const val BASE_URL = "https://ja.wikipedia.org/w/api.php/"
    }

    @GET(".")
    fun getRandomTitles(@Query("action") action: String = "query",
                        @Query("list") list: String = "random",
                        @Query("rnnamespace") rnnamespace: Int = 0,
                        @Query("rnlimit") rnlimit: Int = 11,
                        @Query("format") format: String = "json"): Call<TitlesQueryResponse>

    @GET(".")
    fun getExtract(@Query("action") action: String = "query",
                   @Query("prop") prop: String = "extracts|pageimages",
                   // max width of thumbnail,
                   @Query("pithumbsize") pithumbsize: Int = 1080,
                   @Query("explaintext") explaintext: Boolean = true,
                   @Query("exsectionformat") exsectionformat: String = "plain",
                   @Query("format") format: String = "json",
                   @Query("formatversion") formatVersion: Int = 2,
                   @Query("titles", encoded = true) title: String): Call<ExtractsQueryResponse>
}