package com.rob729.newsfeed.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rob729.newsfeed.R
import com.rob729.newsfeed.model.state.UiStatus
import com.rob729.newsfeed.model.state.home.HomeFeedSideEffect
import com.rob729.newsfeed.model.state.home.HomeFeedState
import com.rob729.newsfeed.model.ui.IconData
import com.rob729.newsfeed.ui.NavigationScreens
import com.rob729.newsfeed.ui.bottomSheet.NewsSourceBottomSheet
import com.rob729.newsfeed.ui.components.LoadingView
import com.rob729.newsfeed.ui.components.NewsFeedItem
import com.rob729.newsfeed.ui.components.NewsSourceExtendedFab
import com.rob729.newsfeed.ui.components.NoInternetView
import com.rob729.newsfeed.ui.components.PaginationLoader
import com.rob729.newsfeed.ui.components.ScrollToTopFab
import com.rob729.newsfeed.ui.components.Toolbar
import com.rob729.newsfeed.ui.theme.lexendDecaFontFamily
import com.rob729.newsfeed.utils.CommonUtils.openNewsArticle
import com.rob729.newsfeed.utils.Constants
import com.rob729.newsfeed.vm.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel(),
    paddingValues: PaddingValues,
) {
    var isNewsSourceBottomSheetVisible by rememberSaveable { mutableStateOf(false) }

    val listState = rememberLazyListState()
    val newsState = viewModel.collectAsState().value
    val bottomSheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    val isMinItemsScrolledForScrollToTopVisibility by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 2
        }
    }

    val toolbarElevation by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex == 0) {
                minOf(
                    listState.firstVisibleItemScrollOffset.toFloat().dp,
                    Constants.MAX_TOOLBAR_ELEVATION.dp,
                )
            } else {
                Constants.MAX_TOOLBAR_ELEVATION.dp
            }
        }
    }

    viewModel.collectSideEffect {
        when (it) {
            is HomeFeedSideEffect.NewsSourceClicked -> {
                withContext(Dispatchers.Main.immediate) {
                    bottomSheetState.hide()
                    isNewsSourceBottomSheetVisible = false
                }
                listState.scrollToItem(0)
            }

            is HomeFeedSideEffect.FeedItemClicked -> {
                openNewsArticle(
                    context,
                    it.selectedItemUrl,
                    newsState.shouldOpenLinksUsingInAppBrowser,
                )
            }

            HomeFeedSideEffect.NewsSourceFabClicked -> {
                isNewsSourceBottomSheetVisible = true
            }

            HomeFeedSideEffect.ScrollToTopClicked -> {
                listState.animateScrollToItem(0)
            }
        }
    }

    LaunchedEffect(listState, newsState.uiStatus) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo
                .lastOrNull()
                ?.index
        }.collect { lastVisibleItemIndex ->
            (newsState.uiStatus as? UiStatus.Success)?.let { state ->
                if (state.paginationData.shouldTriggerPagination() &&
                    lastVisibleItemIndex != null && lastVisibleItemIndex
                    >= listState.layoutInfo.totalItemsCount - Constants.PAGINATION_TRIGGER_THRESHOLD
                ) {
                    viewModel.fetchMoreNewsArticles()
                }
            }
        }
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
                    bottom = paddingValues.calculateBottomPadding(),
                ),
    ) {
        Column {
            HomeScreenToolbar(
                toolbarElevation,
                navController,
                Modifier.padding(top = paddingValues.calculateTopPadding()),
            )

            DisplayNewsFeed(newsState, listState, viewModel)
        }

        if (newsState.uiStatus is UiStatus.Success) {
            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomStart),
                visible = listState.isScrollingUp() && isMinItemsScrolledForScrollToTopVisibility,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                ScrollToTopFab(
                    modifier =
                        Modifier
                            .padding(16.dp)
                            .testTag("scroll_up_fab"),
                    onClick = { viewModel.scrollToTopClicked() },
                )
            }

            NewsSourceExtendedFab(
                modifier =
                    Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd)
                        .testTag("news_source_fab"),
                isExpanded = listState.isScrollingUp(),
                onClick = {
                    viewModel.newsSourceFabClicked()
                },
            )
        }
    }

    NewsSourceBottomSheet(
        newsState.newsSources,
        bottomSheetState,
        isNewsSourceBottomSheetVisible,
        viewModel::newsSourceClicked,
        newsState.selectedNewsSource,
    ) {
        isNewsSourceBottomSheetVisible = false
    }

    BackHandler(isNewsSourceBottomSheetVisible) {
        isNewsSourceBottomSheetVisible = false
    }
}

@Composable
private fun HomeScreenToolbar(
    toolbarElevation: Dp,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Toolbar(
        Constants.HOME_TOOLBAR_TITLE,
        modifier,
        null,
        painterResource(id = R.mipmap.ic_launcher_foreground),
        IconData(Icons.Default.Search) {
            navController.navigate(NavigationScreens.SEARCH.routeName)
        },
        IconData(Icons.Default.Bookmarks) {
            navController.navigate(NavigationScreens.BOOKMARKED_ARTICLES.routeName)
        },
        toolbarElevation,
    ) { isOverflowMenuExpanded, dismissOverflowMenu ->
        DropdownMenu(
            expanded = isOverflowMenuExpanded,
            onDismissRequest = dismissOverflowMenu,
            content = {
                MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape((12.dp)))) {
                    DropdownMenuItem(text = {
                        Text(
                            text = Constants.OVERFLOW_MENU_ITEM_SETTINGS,
                            fontFamily = lexendDecaFontFamily,
                            fontWeight = FontWeight.Medium,
                        )
                    }, onClick = {
                        dismissOverflowMenu()
                        navController.navigate(NavigationScreens.SETTINGS.routeName)
                    })
                }
            },
        )
    }
}

@Composable
private fun DisplayNewsFeed(
    newsState: HomeFeedState,
    listState: LazyListState,
    viewModel: HomeViewModel,
) {
    when (newsState.uiStatus) {
        UiStatus.Error -> {
            NoInternetView(viewModel::tryAgainClicked)
        }

        UiStatus.Loading -> {
            LoadingView()
        }

        is UiStatus.Success -> {
            LazyColumn(Modifier.testTag("news_list"), listState) {
                items(newsState.uiStatus.newsEntityUiData.articles, key = {
                    "${it.url} ${it.publishedAt}"
                }) { item ->
                    NewsFeedItem(
                        Modifier,
                        newsArticleUiData = item,
                        false,
                        { viewModel.newsFeedItemClicked(item) },
                        { isBookmarked ->
                            viewModel.newsFeedItemBookmarkClicked(
                                item,
                                isBookmarked,
                            )
                        },
                    )
                }

                if (newsState.uiStatus.paginationData.showPaginationLoader) {
                    item { PaginationLoader() }
                }
            }
        }

        else -> {}
    }
}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}
