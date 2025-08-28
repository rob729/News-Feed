package com.rob729.newsfeed.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rob729.newsfeed.AppPreferences
import com.rob729.newsfeed.model.ui.IconData
import com.rob729.newsfeed.ui.components.DropDownPreference
import com.rob729.newsfeed.ui.components.Separator
import com.rob729.newsfeed.ui.components.SettingsCategory
import com.rob729.newsfeed.ui.components.SwitchPreference
import com.rob729.newsfeed.ui.components.Toolbar
import com.rob729.newsfeed.ui.theme.lexendDecaFontFamily
import com.rob729.newsfeed.utils.CommonUtils
import com.rob729.newsfeed.utils.Constants
import com.rob729.newsfeed.utils.Constants.SETTINGS_TOOLBAR_TITLE
import com.rob729.newsfeed.utils.SettingsConstants
import com.rob729.newsfeed.utils.name
import com.rob729.newsfeed.utils.toAppTheme
import com.rob729.newsfeed.vm.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val appPreference =
        viewModel.appPreferencesFlow.collectAsState(initial = AppPreferences.getDefaultInstance())

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateRightPadding(LayoutDirection.Ltr),
                    bottom = paddingValues.calculateBottomPadding(),
                ),
    ) {
        Column {
            Toolbar(
                title = SETTINGS_TOOLBAR_TITLE,
                leftIcon = IconData(Icons.AutoMirrored.Filled.ArrowBack) { navController.popBackStack() },
                toolbarElevation = 12.dp,
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
            )

            Column {
                SettingsCategory(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = SettingsConstants.INTERFACE_SECTION_HEADER_TITLE,
                )

                Spacer(modifier = Modifier.height(8.dp))

                DropDownPreference(
                    title = SettingsConstants.THEME_PREF_TITLE,
                    subtitle = appPreference.value.theme.name(),
                    items = Constants.themes,
                    onItemSelected = {
                        viewModel.updatePreference { appPreferences ->
                            appPreferences.toBuilder().setTheme(it.toAppTheme()).build()
                        }
                    },
                )

                Separator()

                SwitchPreference(
                    title = SettingsConstants.IN_APP_BROWSER_PREF_TITLE,
                    subtitle = SettingsConstants.IN_APP_BROWSER_PREF_SUBTITLE,
                    checked = appPreference.value.shouldOpenLinksUsingInAppBrowser,
                ) { shouldOpenLinksUsingInAppBrowser ->
                    viewModel.updatePreference { appPreference ->
                        appPreference
                            .toBuilder()
                            .setShouldOpenLinksUsingInAppBrowser(
                                shouldOpenLinksUsingInAppBrowser,
                            ).build()
                    }
                }

                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(bottom = 16.dp),
                ) {
                    Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                        Text(
                            text = Constants.HOME_TOOLBAR_TITLE,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            fontFamily = lexendDecaFontFamily,
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "v${CommonUtils.getAppVersion(context)}",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            fontFamily = lexendDecaFontFamily,
                        )
                    }
                }
            }
        }
    }
}
