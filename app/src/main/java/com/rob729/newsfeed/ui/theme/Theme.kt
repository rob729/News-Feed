package com.rob729.newsfeed.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColorPalette = lightColorScheme(
    primary = mdThemeLightPrimary,
    onPrimary = mdThemeLightOnPrimary,
    primaryContainer = mdThemeLightPrimaryContainer,
    onPrimaryContainer = mdThemeLightOnPrimaryContainer,
    secondary = mdThemeLightSecondary,
    onSecondary = mdThemeLightOnSecondary,
    secondaryContainer = mdThemeLightSecondaryContainer,
    onSecondaryContainer = mdThemeLightOnSecondaryContainer,
    tertiary = mdThemeLightTertiary,
    onTertiary = mdThemeLightOnTertiary,
    tertiaryContainer = mdThemeLightTertiaryContainer,
    onTertiaryContainer = mdThemeLightOnTertiaryContainer,
    error = mdThemeLightError,
    errorContainer = mdThemeLightErrorContainer,
    onError = mdThemeLightOnError,
    onErrorContainer = mdThemeLightOnErrorContainer,
    background = mdThemeLightBackground,
    onBackground = mdThemeLightOnBackground,
    surface = mdThemeLightSurface,
    onSurface = mdThemeLightOnSurface,
    surfaceVariant = mdThemeLightSurfaceVariant,
    onSurfaceVariant = mdThemeLightOnSurfaceVariant,
    outline = mdThemeLightOutline,
    inverseOnSurface = mdThemeLightInverseOnSurface,
    inverseSurface = mdThemeLightInverseSurface,
    inversePrimary = mdThemeLightInversePrimary,
    surfaceTint = mdThemeLightSurfaceTint,
    outlineVariant = mdThemeLightOutlineVariant,
    scrim = mdThemeLightScrim,
)

private val DarkColorPalette = darkColorScheme(
    primary = mdThemeDarkPrimary,
    onPrimary = mdThemeDarkOnPrimary,
    primaryContainer = mdThemeDarkPrimaryContainer,
    onPrimaryContainer = mdThemeDarkOnPrimaryContainer,
    secondary = mdThemeDarkSecondary,
    onSecondary = mdThemeDarkOnSecondary,
    secondaryContainer = mdThemeDarkSecondaryContainer,
    onSecondaryContainer = mdThemeDarkOnSecondaryContainer,
    tertiary = mdThemeDarkTertiary,
    onTertiary = mdThemeDarkOnTertiary,
    tertiaryContainer = mdThemeDarkTertiaryContainer,
    onTertiaryContainer = mdThemeDarkOnTertiaryContainer,
    error = mdThemeDarkError,
    errorContainer = mdThemeDarkErrorContainer,
    onError = mdThemeDarkOnError,
    onErrorContainer = mdThemeDarkOnErrorContainer,
    background = mdThemeDarkBackground,
    onBackground = mdThemeDarkOnBackground,
    surface = mdThemeDarkSurface,
    onSurface = mdThemeDarkOnSurface,
    surfaceVariant = mdThemeDarkSurfaceVariant,
    onSurfaceVariant = mdThemeDarkOnSurfaceVariant,
    outline = mdThemeDarkOutline,
    inverseOnSurface = mdThemeDarkInverseOnSurface,
    inverseSurface = mdThemeDarkInverseSurface,
    inversePrimary = mdThemeDarkInversePrimary,
    surfaceTint = mdThemeDarkSurfaceTint,
    outlineVariant = mdThemeDarkOutlineVariant,
    scrim = mdThemeDarkScrim,
)


@Composable
fun NewsFeedTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}