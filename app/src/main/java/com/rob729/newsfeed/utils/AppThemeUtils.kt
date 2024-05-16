package com.rob729.newsfeed.utils

import androidx.annotation.StringDef
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.rob729.newsfeed.AppPreferences.AppTheme

@StringDef(Theme.SYSTEM, Theme.LIGHT, Theme.DARK)
annotation class Theme {
    companion object {
        const val SYSTEM = "System Default"
        const val LIGHT = "Light"
        const val DARK = "Dark"
    }
}

fun AppTheme.name() = when (this) {
    AppTheme.LIGHT -> Theme.LIGHT
    AppTheme.DARK -> Theme.DARK
    else -> Theme.SYSTEM
}

fun String.toAppTheme() = when (this) {
    Theme.LIGHT -> AppTheme.LIGHT
    Theme.DARK -> AppTheme.DARK
    Theme.SYSTEM -> AppTheme.SYSTEM_DEFAULT
    else -> null
}

@Composable
fun AppTheme.isDarkThemeEnabled() = when (this) {
    AppTheme.LIGHT -> false
    AppTheme.DARK -> true
    else -> isSystemInDarkTheme()
}
