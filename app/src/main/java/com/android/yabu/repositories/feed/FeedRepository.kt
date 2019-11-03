package com.android.yabu.repositories.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.yabu.repositories.*
import com.android.yabu.repositories.feed.source.FeedCache
import com.android.yabu.repositories.feed.source.network.ExtractsQueryResponse
import com.android.yabu.repositories.feed.source.network.WikipediaWebservice
import com.android.yabu.utils.LogUtils
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for the [Feed] data.
 * @property webservice
 * @property cache
 * @property viewModelScope
 */
// Informs Dagger that this class should be constructed only once.
@Singleton
class FeedRepository @Inject constructor(
    private val webservice: WikipediaWebservice,
    private val cache: FeedCache,
    private val viewModelScope: CoroutineScope) {

    /**
     * Fetches a [Feed] [Response].
     * @return a [LiveData] wrapped response.
     */
    fun fetchFeed(): LiveData<Resource<Feed>> {
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
                    webservice.getExtract(title = title.title).execute().body()
                }

                if (extractResponses.isEmpty()) {
                    LogUtils.warn("Failed getting extract responses.")
                    return Failure(404, "Error fetching extracts")
                }

                LogUtils.warn(extractResponses[0].query.pages[0].title)

                return Success(extractResponses)
            }

            override fun processResponse(response: List<ExtractsQueryResponse>): Feed {
                // create a new feed and populate the articles,
                val feed = Feed()
                feed.articles = response.map { extracts ->
                    val page = extracts.query.pages[0]
                    Article(page.pageid, page.title, page.extract)
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