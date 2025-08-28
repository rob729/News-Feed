package com.rob729.newsfeed.model.api

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Keep
@JsonClass(generateAdapter = true)
data class NetworkArticle(
    @Json(name = "title") val title: String?,
    @Json(name = "url") val url: String,
    @Json(name = "urlToImage") val imageUrl: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "publishedAt") val publishedAt: String,
    @Json(name = "source") val source: ArticleSource? = null,
) : Serializable

@Keep
@JsonClass(generateAdapter = true)
data class ArticleSource(
    @Json(name = "name") val name: String? = null,
) : Serializable
