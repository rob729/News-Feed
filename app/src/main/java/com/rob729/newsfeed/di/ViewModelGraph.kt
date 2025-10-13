package com.rob729.newsfeed.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.zacsweers.metro.GraphExtension
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provider
import kotlin.reflect.KClass

@GraphExtension(ViewModelScope::class)
interface ViewModelGraph {

    @Multibinds
    val viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>>

    val viewModelProviderFactory: ViewModelProvider.Factory
}
