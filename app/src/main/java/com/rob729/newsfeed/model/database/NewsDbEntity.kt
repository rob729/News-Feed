package com.rob729.newsfeed.model.database

import androidx.room.ColumnInfo
import java.io.Serializable

data class NewsDbEntity(
    @ColumnInfo(name = "articles") val articles: List<ArticleDbData>,
    @ColumnInfo(name = "totalResultCount") val totalResultCount: Int
): Serializable
