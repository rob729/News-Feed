package com.rob729.newsfeed.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rob729.newsfeed.AppPreferences
import com.rob729.newsfeed.repository.PreferenceRepository
import com.rob729.newsfeed.ui.screen.BookmarkedArticlesScreen
import com.rob729.newsfeed.ui.screen.HomeScreen
import com.rob729.newsfeed.ui.screen.SearchScreen
import com.rob729.newsfeed.ui.screen.SettingsScreen
import com.rob729.newsfeed.ui.theme.NewsFeedTheme
import com.rob729.newsfeed.utils.Constants.ANIMATION_DURATION
import com.rob729.newsfeed.utils.NotificationHelper
import com.rob729.newsfeed.utils.isDarkThemeEnabled
import org.koin.android.ext.android.inject

@OptIn(ExperimentalComposeUiApi::class)
class NewsActivity : ComponentActivity() {
    private val notificationHelper: NotificationHelper by lazy {
        NotificationHelper(baseContext)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                notificationHelper.scheduleNotification()
            }
        }

    private val preferenceRepository: PreferenceRepository by inject()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
        }
        requestNotificationPermission()
        setContent {
            val navController = rememberNavController()
            val appTheme =
                preferenceRepository
                    .getAppTheme()
                    .collectAsState(initial = AppPreferences.AppTheme.SYSTEM_DEFAULT)
            NewsFeedTheme(appTheme.value.isDarkThemeEnabled()) {
                // A surface container using the 'background' color from the theme
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->

                    NavHost(
                        navController = navController,
                        startDestination = NavigationScreens.HOME.routeName,
                        modifier =
                            Modifier
                                .semantics { testTagsAsResourceId = true }
                                .consumeWindowInsets(paddingValues),
                    ) {
                        composable(NavigationScreens.HOME.routeName) {
                            HomeScreen(navController, paddingValues = paddingValues)
                        }

                        composable(
                            NavigationScreens.SEARCH.routeName,
                            enterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                    animationSpec = tween(ANIMATION_DURATION),
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                                    animationSpec = tween(ANIMATION_DURATION),
                                )
                            },
                        ) {
                            SearchScreen(navController, paddingValues)
                        }

                        composable(
                            NavigationScreens.BOOKMARKED_ARTICLES.routeName,
                            enterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                    animationSpec = tween(ANIMATION_DURATION),
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                                    animationSpec = tween(ANIMATION_DURATION),
                                )
                            },
                        ) {
                            BookmarkedArticlesScreen(navController, paddingValues)
                        }

                        composable(
                            NavigationScreens.SETTINGS.routeName,
                            enterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                    animationSpec = tween(ANIMATION_DURATION),
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                                    animationSpec = tween(ANIMATION_DURATION),
                                )
                            },
                        ) {
                            SettingsScreen(navController, paddingValues)
                        }
                    }
                }
            }
        }
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                baseContext,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS,
                )
            }
        } else {
            notificationHelper.scheduleNotification()
        }
    }
}
