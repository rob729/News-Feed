package com.rob729.newsfeed.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rob729.newsfeed.AppPreferences
import com.rob729.newsfeed.repository.PreferenceRepository
import kotlinx.coroutines.launch

class SettingsViewModel(private val preferenceRepository: PreferenceRepository) : ViewModel() {

    val appPreferencesFlow = preferenceRepository.data

    fun updatePreference(update: (AppPreferences) -> AppPreferences) {
        viewModelScope.launch {
            preferenceRepository.update(update)
        }
    }
}
