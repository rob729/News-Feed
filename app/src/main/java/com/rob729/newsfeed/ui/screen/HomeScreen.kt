package com.rob729.newsfeed.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rob729.newsfeed.model.state.UiStatus
import com.rob729.newsfeed.model.state.home.HomeFeedSideEffect
import com.rob729.newsfeed.ui.NavigationScreens
import com.rob729.newsfeed.ui.components.LoadingView
import com.rob729.newsfeed.ui.components.NewsFeedItem
import com.rob729.newsfeed.ui.components.NewsSourceBottomSheetContent
import com.rob729.newsfeed.ui.components.NewsSourceExtendedFab
import com.rob729.newsfeed.ui.components.NoInternetView
import com.rob729.newsfeed.ui.components.ScrollToTopFab
import com.rob729.newsfeed.ui.components.Toolbar
import com.rob729.newsfeed.vm.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(
    ExperimentalMaterialApi::class,
)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel(),
    paddingValues: PaddingValues,
    openCustomTab: (url: String) -> Unit
) {

    val listState = rememberLazyListState()

    val newsState = viewModel.collectAsState().value

    val isMinItemsScrolledForScrollToTopVisibility by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 2
        }
    }

    val toolbarElevation by remember {
        derivedStateOf {
            if(listState.firstVisibleItemIndex == 0) {
                minOf(listState.firstVisibleItemScrollOffset.toFloat().dp, 12.dp)
            } else {
                12.dp
            }
        }
    }

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    )

    val coroutineScope = rememberCoroutineScope()

    viewModel.collectSideEffect {
        when (it) {
            is HomeFeedSideEffect.HomeSourceClicked -> {
                coroutineScope.launch(Dispatchers.Main.immediate) {
                    modalSheetState.hide()
                }
                listState.scrollToItem(0)
            }
            is HomeFeedSideEffect.HomeFeedItemClicked -> {
                openCustomTab(it.selectedItemUrl)
            }
            HomeFeedSideEffect.HomeSourceFabClicked -> {
                coroutineScope.launch {
                    modalSheetState.show()
                }
            }
            HomeFeedSideEffect.ScrollToTopClicked -> {
                listState.animateScrollToItem(0)
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        scrimColor = Color.Black.copy(alpha = 0.6f),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            NewsSourceBottomSheetContent(
                onNewsSourceClicked = {
                    viewModel.newsSourceClicked(it)
                },
                currentSelectedNewsSource = newsState.selectedNewsSource
            )
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            Column {
                Toolbar(toolbarElevation) {
                    navController.navigate(NavigationScreens.SEARCH.routeName)
                }

                when (newsState.uiStatus) {
                    UiStatus.Error -> {
                        NoInternetView(viewModel::tryAgainClicked)
                    }
                    UiStatus.Loading -> {
                        LoadingView()
                    }
                    is UiStatus.Success -> {
                        LazyColumn(Modifier.testTag("news_list"), listState) {
                            items(newsState.uiStatus.news) { item ->
                                NewsFeedItem(newsArticleUiData = item) {
                                    viewModel.newsFeedItemClicked(item)
                                }
                            }
                        }
                    }

                    else -> {}
                }
            }

            if(newsState.uiStatus is UiStatus.Success) {
                AnimatedVisibility(modifier = Modifier.align(Alignment.BottomStart), visible = listState.isScrollingUp() && isMinItemsScrolledForScrollToTopVisibility, enter = fadeIn(), exit = fadeOut()) {
                    ScrollToTopFab(modifier = Modifier
                        .padding(16.dp)
                        .testTag("scroll_up_fab"), onClick = { viewModel.scrollToTopClicked() })
                }

                NewsSourceExtendedFab(modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
                    .testTag("news_source_fab"), isExpanded = listState.isScrollingUp(),
                    onClick = {
                        viewModel.newsSourceFabClicked()
                    })
            }
        }
    }

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
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