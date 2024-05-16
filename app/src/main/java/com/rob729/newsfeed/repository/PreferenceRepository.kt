package com.rob729.newsfeed.repository

import androidx.datastore.core.DataStore
import com.rob729.newsfeed.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceRepository(private val appPreferences: DataStore<AppPreferences>) {

    val data = appPreferences.data

    suspend fun update(update: (AppPreferences) -> AppPreferences) {
        appPreferences.updateData(update)
    }

    fun getAppTheme(): Flow<AppPreferences.AppTheme> {
        return data.map { it.theme }
    }

    fun getNewsSources(): Flow<List<AppPreferences.NewsSource>> {
        return data.map { it.newsSourcesList }
    }

    fun shouldOpenLinksUsingInAppBrowser(): Flow<Boolean> =
        data.map { it.shouldOpenLinksUsingInAppBrowser }
}
