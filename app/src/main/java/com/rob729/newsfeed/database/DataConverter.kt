package com.rob729.newsfeed.database

import androidx.room.TypeConverter
import com.rob729.newsfeed.model.database.NewsDbEntity
import kotlinx.serialization.json.Json

class DataConverter {
    @TypeConverter
    fun toJson(newsDbEntity: NewsDbEntity): String {
        return Json.encodeToString(newsDbEntity)
    }

    @TypeConverter
    fun toNewsDbEntity(json: String): NewsDbEntity {
        return Json.decodeFromString<NewsDbEntity>(json)
    }
}
