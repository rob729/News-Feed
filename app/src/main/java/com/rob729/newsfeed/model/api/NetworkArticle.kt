package com.rob729.newsfeed.model.api

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import java.io.Serializable

@Keep
@kotlinx.serialization.Serializable
data class NetworkArticle(
    @SerialName("title") val title: String?,
    @SerialName( "url") val url: String,
    @SerialName( "urlToImage") val imageUrl: String?,
    @SerialName( "description") val description: String?,
    @SerialName( "publishedAt") val publishedAt: String,
    @SerialName( "source") val source: ArticleSource? = null,
) : Serializable

@Keep
@kotlinx.serialization.Serializable
data class ArticleSource(
    @SerialName("name") val name: String? = null,
) : Serializable
