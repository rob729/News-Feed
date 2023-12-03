package com.rob729.newsfeed.database

import androidx.room.TypeConverter
import com.rob729.newsfeed.model.database.ArticleDbData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class DataConverter {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @TypeConverter
    fun toJson(articleDbData: ArticleDbData): String {
        val jsonAdapter: JsonAdapter<ArticleDbData> = moshi.adapter(ArticleDbData::class.java)
        return jsonAdapter.toJson(articleDbData)
    }


    @TypeConverter
    fun toArticleDbDataList(json: String): ArticleDbData? {
        val jsonAdapter: JsonAdapter<ArticleDbData> = moshi.adapter(ArticleDbData::class.java)
        return jsonAdapter.fromJson(json)
    }
}
