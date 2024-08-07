package com.rob729.newsfeed.network

import com.rob729.newsfeed.model.api.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getNews(
        @Query("domains") domain: String,
        @Query("apiKey") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): Response<NewsApiResponse>


    @GET("everything")
    suspend fun getNewsSearchResults(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String,
        @Query("from") startDate: String,
        @Query("sortBy") sortBy: String,
        @Query("language") language: String,
        @Query("pageSize") pageSize: Int,
    ): Response<NewsApiResponse>
}
