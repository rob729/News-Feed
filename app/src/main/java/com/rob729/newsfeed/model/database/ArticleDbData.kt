package com.rob729.newsfeed.model.database

import androidx.room.ColumnInfo

data class ArticleDbData(
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "urlToImage") val imageUrl: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "publishedAt") val publishedAt: String,
    @ColumnInfo(name = "source") val source: String?
)
