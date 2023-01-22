package com.rob729.newsfeed.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rob729.newsfeed.R
import com.rob729.newsfeed.model.state.NewsFeedSideEffect
import com.rob729.newsfeed.ui.components.LoadingShimmer
import com.rob729.newsfeed.ui.components.NewsFeedItem
import com.rob729.newsfeed.ui.theme.lexendDecaFontFamily
import com.rob729.newsfeed.utils.Constants
import com.rob729.newsfeed.vm.NewsViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalFoundationApi::class)
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

    viewModel.collectSideEffect {
        when (it) {
            is NewsFeedSideEffect.NewsSourceClicked -> {
                listState.scrollToItem(0)
            }
            NewsFeedSideEffect.NewsfeedItemClicked -> {
                openCustomTab(newsState.selectedNewsUrl)
            }
            NewsFeedSideEffect.NewsSourceFabClicked -> {

            }
            NewsFeedSideEffect.ScrollToTopClicked -> {
                listState.animateScrollToItem(0)
            }
        }
    }

    if (newsState.showDialog) {
        showNewsSourceDialog(
            { viewModel.dismissNewsSourceDialog() },
            { selectedNewsSource ->
                viewModel.newsSourceClicked(selectedNewsSource)
            },
            newsState.selectedNewsSource
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            Surface(elevation = 8.dp, color = colorResource(R.color.status_bar)) {
                Row(modifier = Modifier) {
                    Text(
                        text = "News Feed",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        fontFamily = lexendDecaFontFamily,
                        color = Color.White
                    )
                }
            }
            if (newsState.isLoading) {
                LazyColumn {
                    repeat(4) {
                        item {
                            LoadingShimmer()
                        }
                    }
                }
            } else {
                LazyColumn(Modifier, listState) {
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

        SmallFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp)
                .offset(y = if (listState.isScrollingUp() && !isFirstItemVisible.value) (-16).dp else 48.dp),
            onClick = { viewModel.scrollToTopClicked() },
            containerColor = colorResource(R.color.status_bar)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                tint = Color.White,
                contentDescription = Constants.FAB_TITLE
            )
        }

        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            onClick = {
                viewModel.newsSourceFabClicked()
            },
            text = { Text(text = Constants.FAB_TITLE, color = Color.White) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Newspaper,
                    tint = Color.White,
                    contentDescription = Constants.FAB_TITLE
                )
            },
            elevation = FloatingActionButtonDefaults.elevation(8.dp),
            expanded = listState.isScrollingUp(),
            containerColor = colorResource(R.color.status_bar)
        )
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