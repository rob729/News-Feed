package com.rob729.newsfeed.repository

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.rob729.newsfeed.AppPreferences
import com.rob729.newsfeed.AppPreferences.NewsSource
import com.rob729.newsfeed.utils.Constants
import java.io.InputStream
import java.io.OutputStream

object AppPreferencesSerializer : Serializer<AppPreferences> {
    override val defaultValue: AppPreferences
        get() = AppPreferences.newBuilder()
            .setTheme(AppPreferences.AppTheme.SYSTEM_DEFAULT)
            .setShouldOpenLinksUsingInAppBrowser(true)
            .addAllNewsSources(Constants.newsSourceUiDataLists.map {
                NewsSource.newBuilder().setName(it.name).setImageUrl(it.imageUrl)
                    .setDomainUrl(it.domain).build()
            })
            .build()

    override suspend fun readFrom(input: InputStream): AppPreferences {
        try {
            return AppPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: AppPreferences, output: OutputStream) = t.writeTo(output)
}
