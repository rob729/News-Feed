package com.rob729.newsfeed.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_source_table")
data class NewsSourceDbData(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "news_source_domain") val newsSourceDomain: String,
    @ColumnInfo(name = "news_entity") val newsDbEntity: NewsDbEntity,
    @ColumnInfo(name = "news_source_fetch_time") val newsSourceFetchTimeInMillis: Long,
    @ColumnInfo(name = "page") val page: Int
)
