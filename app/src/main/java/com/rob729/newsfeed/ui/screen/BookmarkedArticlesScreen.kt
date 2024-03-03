package com.rob729.newsfeed.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rob729.newsfeed.model.state.bookmarkedArticles.BookmarkedArticleSideEffect
import com.rob729.newsfeed.model.ui.IconData
import com.rob729.newsfeed.ui.components.NewsFeedItem
import com.rob729.newsfeed.ui.components.NoBookmarkedItems
import com.rob729.newsfeed.ui.components.Toolbar
import com.rob729.newsfeed.utils.Constants.BOOKMARK_TOOLBAR_TITLE
import com.rob729.newsfeed.utils.Constants.MAX_TOOLBAR_ELEVATION
import com.rob729.newsfeed.vm.BookmarkedArticlesVM
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkedArticlesScreen(
    navController: NavHostController,
    viewModel: BookmarkedArticlesVM = koinViewModel(),
    openCustomTab: (url: String) -> Unit
) {

    val bookmarkedArticlesState = viewModel.collectAsState().value

    val listState = rememberLazyListState()
    val toolbarElevation by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex == 0) {
                minOf(listState.firstVisibleItemScrollOffset.toFloat().dp, MAX_TOOLBAR_ELEVATION.dp)
            } else {
                MAX_TOOLBAR_ELEVATION.dp
            }
        }
    }

    viewModel.collectSideEffect {
        when (it) {
            is BookmarkedArticleSideEffect.BookmarkedArticleClicked -> {
                openCustomTab(it.selectedItemUrl)
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Column {
            Toolbar(
                title = BOOKMARK_TOOLBAR_TITLE,
                leftIcon = IconData(Icons.AutoMirrored.Filled.ArrowBack) { navController.popBackStack() },
                toolbarElevation = toolbarElevation
            )

            if (bookmarkedArticlesState.bookmarkedArticles.isEmpty()) {
                NoBookmarkedItems()
            } else {
                LazyColumn(Modifier.testTag("bookmarked_news_list"), listState) {
                    items(bookmarkedArticlesState.bookmarkedArticles, key = {
                        it.url
                    }) { item ->
                        NewsFeedItem(
                            Modifier.animateItemPlacement(),
                            newsArticleUiData = item,
                            true,
                            { viewModel.newsFeedItemClicked(item) },
                            { isBookmarked ->
                                viewModel.newsFeedItemBookmarkClicked(
                                    item,
                                    isBookmarked
                                )
                            })
                    }
                }
            }
        }
    }
}
