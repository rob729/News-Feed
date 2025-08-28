package com.rob729.newsfeed.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class SearchHistoryHelper(
    private val dataStore: DataStore<Preferences>,
) {
    private val searchHistoryListPrefKey = stringSetPreferencesKey("search_history_list")

    val searchHistoryFlow =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map {
                it[searchHistoryListPrefKey]
            }

    suspend fun addSearchQueryToHistoryList(searchQuery: String) {
        if (searchQuery.isEmpty()) {
            return
        }
        dataStore.edit { pref ->
            pref[searchHistoryListPrefKey]
                ?.filterNot { searchQuery.contains(it) }
                ?.toMutableList()
                ?.also {
                    it.add(searchQuery)
                    pref[searchHistoryListPrefKey] = it.toSet()
                } ?: run {
                pref[searchHistoryListPrefKey] = setOf(searchQuery)
            }
        }
    }

    suspend fun clearSearchHistory() {
        dataStore.edit { pref ->
            pref[searchHistoryListPrefKey] = setOf()
        }
    }
}
