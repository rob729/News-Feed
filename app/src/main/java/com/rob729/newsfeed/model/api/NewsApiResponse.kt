package com.rob729.newsfeed.model.api

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Keep
@JsonClass(generateAdapter = true)
class NewsApiResponse(
    @Json(name = "articles") val networkArticles: List<NetworkArticle>,
    @Json(name = "totalResults") val totalResultCount: Int,
) : Serializable
