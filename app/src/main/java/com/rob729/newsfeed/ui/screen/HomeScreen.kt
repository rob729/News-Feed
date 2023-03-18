package com.rob729.newsfeed.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
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
import com.rob729.newsfeed.model.state.NewsFeedSideEffect
import com.rob729.newsfeed.ui.components.LoadingShimmer
import com.rob729.newsfeed.ui.components.NewsFeedItem
import com.rob729.newsfeed.ui.components.NewsSourceBottomSheetContent
import com.rob729.newsfeed.ui.components.NewsSourceExtendedFab
import com.rob729.newsfeed.ui.components.ScrollToTopFab
import com.rob729.newsfeed.ui.components.Toolbar
import com.rob729.newsfeed.vm.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterialApi::class,
)
@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    openCustomTab: (url: String) -> Unit
) {

    val listState = rememberLazyListState()

    val newsState = viewModel.collectAsState().value

    val isFirstItemVisible = remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    )

    val coroutineScope = rememberCoroutineScope()

    viewModel.collectSideEffect {
        when (it) {
            is NewsFeedSideEffect.NewsSourceClicked -> {
                coroutineScope.launch(Dispatchers.Main.immediate) {
                    modalSheetState.animateTo(ModalBottomSheetValue.Hidden)
                }
                listState.scrollToItem(0)
            }
            NewsFeedSideEffect.NewsFeedItemClicked -> {
                openCustomTab(newsState.selectedNewsUrl)
            }
            NewsFeedSideEffect.NewsSourceFabClicked -> {
                coroutineScope.launch {
                    modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
            }
            NewsFeedSideEffect.ScrollToTopClicked -> {
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
                .background(Color.Black)
        ) {
            Column {
                Toolbar()

                if (newsState.isLoading) {
                    LazyColumn {
                        repeat(4) {
                            item {
                                LoadingShimmer()
                            }
                        }
                    }
                } else {
                    LazyColumn(Modifier.testTag("news_list"), listState) {
                        items(newsState.news) { item ->
                            NewsFeedItem(newsArticleUiData = item,
                                Modifier
                                    .animateItemPlacement()
                                    .clickable {
                                        viewModel.newsFeedItemClicked(item)
                                    })
                        }
                    }
                }
            }

            ScrollToTopFab(modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp)
                .offset(y = if (listState.isScrollingUp() && !isFirstItemVisible.value) (-16).dp else 48.dp)
                .testTag("scroll_up_fab"), onClick = { viewModel.scrollToTopClicked() })

            NewsSourceExtendedFab(modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
                .testTag("news_source_fab"), isExpanded = listState.isScrollingUp(),
                onClick = {
                    viewModel.newsSourceFabClicked()
                })
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