package com.rob729.newsfeed.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rob729.newsfeed.AppPreferences
import com.rob729.newsfeed.di.ViewModelKey
import com.rob729.newsfeed.di.ViewModelScope
import com.rob729.newsfeed.repository.PreferenceRepository
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import kotlinx.coroutines.launch

@ContributesIntoMap(ViewModelScope::class, binding<ViewModel>())
@ViewModelKey(SettingsViewModel::class)
@Inject
class SettingsViewModel(
    private val preferenceRepository: PreferenceRepository,
) : ViewModel() {
    val appPreferencesFlow = preferenceRepository.data

    fun updatePreference(update: (AppPreferences) -> AppPreferences) {
        viewModelScope.launch {
            preferenceRepository.update(update)
        }
    }
}
