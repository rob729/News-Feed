package com.rob729.newsfeed.vm

import com.rob729.newsfeed.database.NewsDBDataSource
import com.rob729.newsfeed.model.NewsResource
import com.rob729.newsfeed.model.api.NetworkNews
import com.rob729.newsfeed.network.NewsApiDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.io.IOException

class NewsRepository(
    private val newsDBDataSource: NewsDBDataSource,
    private val newsApiDataSource: NewsApiDataSource
) {
    suspend fun getNewsArticles(newsSourceDomain: String): Flow<NewsResource> = flow {
        emit(NewsResource.Loading)
        val newsArticlesFromDb = newsDBDataSource.getNewsFromNewsSourceDomain(newsSourceDomain)
        val newsSourceFetchTimeInMillis =
            newsDBDataSource.getNewsSourceFetchTimeInMillis(newsSourceDomain)
        if (newsArticlesFromDb.isNullOrEmpty() || checkIfNewsSourceDataIsOutdated(
                newsSourceFetchTimeInMillis
            )
        ) {
            emit(fetchNewsFromNetworkAndSyncDatabase(newsSourceDomain))
        } else {
            emit(NewsResource.Success(newsArticlesFromDb))
        }
    }

    private suspend fun fetchNewsFromNetworkAndSyncDatabase(newsSourceDomain: String): NewsResource {
        try {
            val newsResource = newsApiDataSource.getNews(newsSourceDomain)
            val currentTimeInMilliSeconds = Clock.System.now().toEpochMilliseconds()
            return if (newsResource is NewsResource.Success<*> && newsResource.data is NetworkNews) {
                newsDBDataSource.setNewsForNewsSourceDomain(
                    newsSourceDomain,
                    newsResource.data.networkArticles,
                    currentTimeInMilliSeconds
                )
                NewsResource.Success(newsDBDataSource.getNewsFromNewsSourceDomain(newsSourceDomain))
            } else {
                newsResource
            }
        } catch (e: IOException) {
            return NewsResource.Error("No Internet")
        }
    }

    fun getSearchResults(query: String): Flow<NewsResource> = flow {
        emit(NewsResource.Loading)
        try {
            emit(newsApiDataSource.getNewsSearchResults(query))
        } catch (e: IOException) {
            emit(NewsResource.Error("No Internet"))
        }
    }

    private fun checkIfNewsSourceDataIsOutdated(newsSourceFetchTimeInMillis: Long?): Boolean {
        if (newsSourceFetchTimeInMillis == null)
            return true
        return (Clock.System.now() - Instant.fromEpochMilliseconds(newsSourceFetchTimeInMillis)).inWholeHours > 6
    }
}