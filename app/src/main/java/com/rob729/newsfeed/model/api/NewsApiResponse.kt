package com.rob729.newsfeed.model.api

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import java.io.Serializable

@Keep
@kotlinx.serialization.Serializable
class NewsApiResponse(
    @SerialName("articles") val networkArticles: List<NetworkArticle>,
    @SerialName("totalResults") val totalResultCount: Int,
) : Serializable
