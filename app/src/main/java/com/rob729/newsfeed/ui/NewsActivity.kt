package com.rob729.newsfeed.ui

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rob729.newsfeed.R
import com.rob729.newsfeed.ui.screen.HomeScreen
import com.rob729.newsfeed.ui.theme.NewsFeedTheme
import com.rob729.newsfeed.utils.Constants
import com.rob729.newsfeed.utils.NotificationHelper
import com.rob729.newsfeed.vm.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsActivity : ComponentActivity() {

    private val newsViewModel: NewsViewModel by viewModel()

    private val notificationHelper: NotificationHelper by lazy {
        NotificationHelper(baseContext)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            notificationHelper.scheduleNotification()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
        setContent {
            NewsFeedTheme {
                // A surface container using the 'background' color from the theme

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = NavigationScreens.HOME.routeName
                ) {
                    composable(NavigationScreens.HOME.routeName) {
                        HomeScreen(newsViewModel) {
                            openCustomTab()
                        }
                    }
                }
            }
        }
    }

    private fun openCustomTab() {
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true)
        builder.setInstantAppsEnabled(true)
        val params = CustomTabColorSchemeParams.Builder()
            .setNavigationBarColor(ContextCompat.getColor(this, R.color.black))
            .setToolbarColor(ContextCompat.getColor(this, R.color.status_bar))
            .build()
        builder.setDefaultColorSchemeParams(params)
        val customBuilder = builder.build()
        customBuilder.intent.setPackage(Constants.CHROME_PACKAGE_NAME)
        customBuilder.launchUrl(
            this,
            Uri.parse(newsViewModel.container.stateFlow.value.selectedNewsUrl)
        )
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                baseContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        } else {
            notificationHelper.scheduleNotification()
        }
    }
}
