package com.rob729.newsfeed.network

import com.rob729.newsfeed.BuildConfig
import com.rob729.newsfeed.model.NewsResource
import com.rob729.newsfeed.utils.Constants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn

class NewsApiDataSourceImpl(
    private val newsApi: NewsApi
) : NewsApiDataSource {

    override suspend fun getNews(domain: String): NewsResource {
        return getDataFromService(
            newsApi.getNews(
                domain,
                BuildConfig.NEWS_FEED_API_KEY,
                Constants.API_RESULT_LANGUAGE
            )
        )
    }

    override suspend fun getNewsSearchResults(query: String): NewsResource {
        val startDate =
            Clock.System.todayIn(TimeZone.currentSystemDefault()).minus(1, DateTimeUnit.MONTH)
                .toString()
        return getDataFromService(
            newsApi.getNewsSearchResults(
                query,
                BuildConfig.NEWS_FEED_API_KEY,
                startDate,
                Constants.SORT_RESULT_FILTER_PUBLISHED_AT,
                Constants.API_RESULT_LANGUAGE
            )
        )
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        NewsResource.Error("${Constants.ERROR_MESSAGE_PREFIX} ${throwable.message}")
    }

    private suspend inline fun <reified T> getDataFromService(result: retrofit2.Response<T>): NewsResource =
        withContext(Dispatchers.IO + coroutineExceptionHandler) {
            return@withContext try {
                if (result.isSuccessful && result.body() != null) {
                    NewsResource.Success(result.body() as T)
                } else {
                    NewsResource.Error(
                        "${Constants.ERROR_MESSAGE_PREFIX} ${result.message()}"
                    )
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                NewsResource.Error("${Constants.ERROR_MESSAGE_PREFIX} ${exception.message}")
            }
        }
}