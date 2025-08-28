package com.rob729.newsfeed.database

import androidx.room.TypeConverter
import com.rob729.newsfeed.model.database.NewsDbEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class DataConverter {
    private val moshi: Moshi =
        Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @TypeConverter
    fun toJson(newsDbEntity: NewsDbEntity): String {
        val jsonAdapter: JsonAdapter<NewsDbEntity> = moshi.adapter(NewsDbEntity::class.java)
        return jsonAdapter.toJson(newsDbEntity)
    }

    @TypeConverter
    fun toNewsDbEntity(json: String): NewsDbEntity? {
        val jsonAdapter: JsonAdapter<NewsDbEntity> = moshi.adapter(NewsDbEntity::class.java)
        return jsonAdapter.fromJson(json)
    }
}
