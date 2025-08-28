package com.rob729.newsfeed.model.database

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import java.io.Serializable

@Keep
data class NewsDbEntity(
    @ColumnInfo(name = "articles") val articles: List<ArticleDbData>,
    @ColumnInfo(name = "totalResultCount") val totalResultCount: Int,
) : Serializable
