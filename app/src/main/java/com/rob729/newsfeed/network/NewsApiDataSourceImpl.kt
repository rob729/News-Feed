package com.rob729.newsfeed.network

import com.rob729.newsfeed.BuildConfig
import com.rob729.newsfeed.model.NewsResource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsApiDataSourceImpl(
    private val newsApi: NewsApi
) : NewsApiDataSource {

    override suspend fun getNews(domain: String): NewsResource {
        return getDataFromService(
            newsApi.getNews(
                domain,
                BuildConfig.NEWS_FEED_API_KEY,
                "en"
            )
        )
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        NewsResource.Error("Something went wrong ${throwable.message}")
    }

    private suspend inline fun <reified T> getDataFromService(result: retrofit2.Response<T>): NewsResource =
        withContext(Dispatchers.IO + coroutineExceptionHandler) {
            return@withContext try {
                if (result.isSuccessful && result.body() != null) {
                    NewsResource.Success(result.body() as T)
                } else {
                    NewsResource.Error(
                        "Something went wrong ${result.message()}"
                    )
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                NewsResource.Error("Something went wrong ${exception.message}")
            }
        }
}