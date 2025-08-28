package com.rob729.newsfeed.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_news_article")
data class BookmarkedNewsArticleDbData(
    @PrimaryKey @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "urlToImage") val imageUrl: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "publishedAt") val publishedAt: String,
    @ColumnInfo(name = "source") val source: String,
)
