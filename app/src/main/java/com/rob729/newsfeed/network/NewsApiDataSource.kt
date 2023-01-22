package com.rob729.newsfeed.network

import com.rob729.newsfeed.model.NewsResource

interface NewsApiDataSource {
    suspend fun getNews(domain: String): NewsResource
}
