package com.android.yabu.repositories.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.yabu.repositories.*
import com.android.yabu.repositories.feed.source.FeedCache
import com.android.yabu.repositories.feed.source.network.ExtractsQueryResponse
import com.android.yabu.repositories.feed.source.network.WikipediaWebservice
import com.android.yabu.utils.LogUtils
import kotlinx.coroutines.CoroutineScope

/**
 * Repository for the [Feed] data.
 * @property webservice
 * @property cache
 * @property viewModelScope
 */
class FeedRepository private constructor(
    private val webservice: WikipediaWebservice,
    private val cache: FeedCache) {

    companion object {

        private var INSTANCE: FeedRepository? = null

        fun getInstance(webservice: WikipediaWebservice,
                        cache: FeedCache): FeedRepository {
            return INSTANCE ?: FeedRepository(webservice, cache)
        }
    }

    /**
     * Fetches a [Feed] [Response].
     * @return a [LiveData] wrapped response.
     */
    fun fetchFeed(viewModelScope: CoroutineScope): LiveData<Resource<Feed>> {
        return object : NetworkResource<Feed, List<ExtractsQueryResponse>>(viewModelScope) {
            override suspend fun loadFromDisk(): LiveData<Feed> {
                return MutableLiveData(cache.readCache())
            }

            override fun shouldFetch(diskResponse: Feed?): Boolean {
                return diskResponse?.articles.isNullOrEmpty()
                        || isExpired(diskResponse?.timestamp)
            }

            override suspend fun fetchData(): Response<List<ExtractsQueryResponse>> {
                // get random titles from the webservice,
                val titlesResponse = webservice.getRandomTitles().execute()

                // if response is valid,
                if (!titlesResponse.isSuccessful || titlesResponse.body() == null) {
                    LogUtils.warn("Failed getting random titles.")
                    return Failure(titlesResponse.code(), titlesResponse.message())
                }

                // map the titles into extract fetch tasks,
                val titles = titlesResponse.body()?.query?.random ?: listOf()
                val extractResponses = titles.mapNotNull { title ->
                    // if response is valid map to list,
                    val response = webservice.getExtract(title = title.title).execute()
                    if (response.isSuccessful && response.body() != null && response.body()?.query != null) {
                        return@mapNotNull response.body()
                    }

                    return@mapNotNull null
                }

                // check if any response has been fetched
                if (extractResponses.isEmpty()) {
                    LogUtils.warn("Failed getting extract responses.")
                    return Failure(404, "Error fetching extracts")
                }

                return Success(extractResponses)
            }

            override fun processResponse(response: List<ExtractsQueryResponse>): Feed {
                // create a new feed and populate the articles,
                val feed = Feed()
                feed.articles = response.mapNotNull { extracts ->
                    val page = extracts.query?.pages?.getOrNull(0)
                    if (page != null) {
                        return@mapNotNull Article(page.pageid, page.title, page.extract, page.thumbnail.source)
                    }

                    return@mapNotNull null
                }

                return feed
            }

            override suspend fun saveToDisk(data: Feed): Boolean {
                return cache.writeCache(data)
            }
        }.asLiveData()
    }

    /**
     * Finds if a timestamp is expired.
     * @param timestamp [FeedTimestamp]
     * @return true if data is same as today.
     */
    fun isExpired(timestamp: FeedTimestamp?): Boolean {
        if (timestamp == null) {
            return true
        }

        return FeedTimestamp().generateTimestamp() != timestamp.generateTimestamp()
    }
}