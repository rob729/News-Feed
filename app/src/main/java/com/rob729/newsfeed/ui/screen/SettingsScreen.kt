package com.rob729.newsfeed.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rob729.newsfeed.AppPreferences
import com.rob729.newsfeed.model.ui.IconData
import com.rob729.newsfeed.ui.components.DropDownPreference
import com.rob729.newsfeed.ui.components.Separator
import com.rob729.newsfeed.ui.components.SettingsCategory
import com.rob729.newsfeed.ui.components.SwitchPreference
import com.rob729.newsfeed.ui.components.Toolbar
import com.rob729.newsfeed.utils.Constants.SETTINGS_TOOLBAR_TITLE
import com.rob729.newsfeed.utils.SettingsConstants
import com.rob729.newsfeed.utils.Theme
import com.rob729.newsfeed.utils.name
import com.rob729.newsfeed.utils.toAppTheme
import com.rob729.newsfeed.vm.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = koinViewModel()
) {

    val appPreference =
        viewModel.appPreferencesFlow.collectAsState(initial = AppPreferences.getDefaultInstance())

    val themes = listOf(
        Theme.SYSTEM to "System default",
        Theme.LIGHT to "Light",
        Theme.DARK to "Dark"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            Toolbar(
                title = SETTINGS_TOOLBAR_TITLE,
                leftIcon = IconData(Icons.AutoMirrored.Filled.ArrowBack) { navController.popBackStack() },
                toolbarElevation = 12.dp
            )

            LazyColumn {
                item {
                    SettingsCategory(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "Interface"
                    )
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }

                item {
                    DropDownPreference(
                        title = SettingsConstants.THEME_PREF_TITLE,
                        subtitle = appPreference.value.theme.name(),
                        items = themes,
                        onItemSelected = {
                            viewModel.updatePreference { appPreferences ->
                                appPreferences.toBuilder().setTheme(it.toAppTheme()).build()
                            }
                        })
                }

                item { Separator() }

                item {
                    SwitchPreference(
                        title = SettingsConstants.IN_APP_BROWSER_PREF_TITLE,
                        subtitle = SettingsConstants.IN_APP_BROWSER_PREF_SUBTITLE,
                        checked = appPreference.value.shouldOpenLinksUsingInAppBrowser
                    ) { shouldOpenLinksUsingInAppBrowser ->
                        viewModel.updatePreference { appPreference ->
                            appPreference.toBuilder().setShouldOpenLinksUsingInAppBrowser(
                                shouldOpenLinksUsingInAppBrowser
                            ).build()
                        }
                    }
                }
            }
        }
    }
}
