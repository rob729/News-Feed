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
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rob729.newsfeed.R
import com.rob729.newsfeed.ui.screen.BookmarkedArticlesScreen
import com.rob729.newsfeed.ui.screen.HomeScreen
import com.rob729.newsfeed.ui.screen.SearchScreen
import com.rob729.newsfeed.ui.theme.NewsFeedTheme
import com.rob729.newsfeed.utils.Constants
import com.rob729.newsfeed.utils.Constants.ANIMATION_DURATION
import com.rob729.newsfeed.utils.NotificationHelper

@OptIn(ExperimentalComposeUiApi::class)
class NewsActivity : ComponentActivity() {

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

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
        setContent {
            val navController = rememberNavController()
            NewsFeedTheme {
                // A surface container using the 'background' color from the theme
                Scaffold { paddingValues ->


                    NavHost(
                        navController = navController,
                        startDestination = NavigationScreens.HOME.routeName,
                        modifier = Modifier.semantics { testTagsAsResourceId = true }
                    ) {
                        composable(NavigationScreens.HOME.routeName) {
                            HomeScreen(navController, paddingValues = paddingValues)
                        }

                        composable(NavigationScreens.SEARCH.routeName,
                            enterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                    animationSpec = tween(ANIMATION_DURATION)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                                    animationSpec = tween(ANIMATION_DURATION)
                                )
                            }) {
                            SearchScreen(navController)
                        }

                        composable(NavigationScreens.BOOKMARKED_ARTICLES.routeName,
                            enterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                    animationSpec = tween(ANIMATION_DURATION)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                                    animationSpec = tween(ANIMATION_DURATION)
                                )
                            }) {
                            BookmarkedArticlesScreen(navController)
                        }
                    }
                }
            }
        }
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
