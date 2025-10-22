package com.rob729.newsfeed.model.database

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ArticleDbData(
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "urlToImage") val imageUrl: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "publishedAt") val publishedAt: String,
    @ColumnInfo(name = "source") val source: String?,
)
