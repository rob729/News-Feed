package com.rob729.newsfeed.model.api

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class NetworkArticle(
    @Json(name = "title") val title: String?,
    @Json(name = "url") val url: String,
    @Json(name = "urlToImage") val imageUrl: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "publishedAt") val publishedAt: String
)