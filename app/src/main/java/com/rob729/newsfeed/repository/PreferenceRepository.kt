package com.rob729.newsfeed.repository

import androidx.datastore.core.DataStore
import com.rob729.newsfeed.AppPreferences
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Inject
class PreferenceRepository(
    private val appPreferences: DataStore<AppPreferences>,
) {
    val data = appPreferences.data

    suspend fun update(update: (AppPreferences) -> AppPreferences) {
        appPreferences.updateData(update)
    }

    fun getAppTheme(): Flow<AppPreferences.AppTheme> = data.map { it.theme }

    fun getNewsSources(): Flow<List<AppPreferences.NewsSource>> = data.map { it.newsSourcesList }

    fun shouldOpenLinksUsingInAppBrowser(): Flow<Boolean> = data.map { it.shouldOpenLinksUsingInAppBrowser }
}
