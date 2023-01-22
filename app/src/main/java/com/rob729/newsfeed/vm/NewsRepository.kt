package com.rob729.newsfeed.vm

import com.rob729.newsfeed.database.NewsDBDataSource
import com.rob729.newsfeed.model.NewsResource
import com.rob729.newsfeed.model.api.NetworkNews
import com.rob729.newsfeed.network.NewsApiDataSource
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class NewsRepository(
    private val newsDBDataSource: NewsDBDataSource,
    private val newsApiDataSource: NewsApiDataSource
) {
    suspend fun getNewsArticles(newsSourceDomain: String): NewsResource {
        val newsArticlesFromDb = newsDBDataSource.getNewsFromNewsSourceDomain(newsSourceDomain)
        val newsSourceFetchTimeInMillis =
            newsDBDataSource.getNewsSourceFetchTimeInMillis(newsSourceDomain)
        return if (newsArticlesFromDb.isNullOrEmpty() || checkIfNewsSourceDataIsOutdated(
                newsSourceFetchTimeInMillis
            )
        ) {
            fetchNewsFromNetworkAndSyncDatabase(newsSourceDomain)
        } else {
            NewsResource.Success(newsArticlesFromDb)
        }
    }

    private suspend fun fetchNewsFromNetworkAndSyncDatabase(newsSourceDomain: String): NewsResource {
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
    }

    private fun checkIfNewsSourceDataIsOutdated(newsSourceFetchTimeInMillis: Long?): Boolean {
        if (newsSourceFetchTimeInMillis == null)
            return true
        return (Clock.System.now() - Instant.fromEpochMilliseconds(newsSourceFetchTimeInMillis)).inWholeHours > 6
    }
}