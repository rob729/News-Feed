package com.rob729.newsfeed.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rob729.newsfeed.model.state.UiStatus
import com.rob729.newsfeed.model.state.search.SearchSideEffects
import com.rob729.newsfeed.ui.components.LoadingView
import com.rob729.newsfeed.ui.components.NoSearchResultsFound
import com.rob729.newsfeed.ui.components.SearchBar
import com.rob729.newsfeed.ui.components.SearchResultItem
import com.rob729.newsfeed.utils.CommonUtils.openNewsArticle
import com.rob729.newsfeed.utils.ScreenType
import com.rob729.newsfeed.vm.SearchViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SearchScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: SearchViewModel = koinViewModel(),
) {
    val searchState = viewModel.collectAsState().value
    val listState = rememberLazyListState()
    val context = LocalContext.current

    viewModel.collectSideEffect {
        when (it) {
            is SearchSideEffects.SearchQueryChanged -> {
                viewModel.addSearchQueryToHistoryList(it.query)
            }

            is SearchSideEffects.SearchResultClicked -> {
                openNewsArticle(
                    context,
                    it.selectedResultUrl,
                    searchState.shouldOpenLinksUsingInAppBrowser,
                )
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
                ).testTag("search_screen_box"),
    ) {
        Column {
            Surface(
                tonalElevation = 4.dp,
                color = MaterialTheme.colorScheme.surfaceVariant,
            ) {
                SearchBar(
                    searchState.editTextInput,
                    { viewModel.updateSearchQuery(it) },
                    { viewModel.updateSearchQuery("") },
                    { navController.popBackStack() },
                    Modifier.padding(top = paddingValues.calculateTopPadding()),
                )
            }

            when (searchState.uiStatus) {
                UiStatus.Error -> {
//                    NoInternetView(viewModel::tryAgainClicked)
                }

                UiStatus.Loading -> {
                    LoadingView(ScreenType.SEARCH)
                }

                is UiStatus.Success -> {
                    if (searchState.uiStatus.newsEntityUiData.articles
                            .isEmpty()
                    ) {
                        NoSearchResultsFound()
                    } else {
                        LazyColumn(Modifier.testTag("search_result_news_list"), listState) {
                            items(searchState.uiStatus.newsEntityUiData.articles) { item ->
                                SearchResultItem(newsArticleUiData = item, onItemClick = {
                                    viewModel.newsFeedItemClicked(item)
                                }, onBookmarkClick = { isBookmarked ->
                                    viewModel.newsFeedItemBookmarkClicked(
                                        item,
                                        isBookmarked,
                                    )
                                })
                            }
                        }
                    }
                }

                is UiStatus.EmptyScreen -> {
                    EmptySearchScreen(
                        searchHistoryList = searchState.searchHistoryList,
                        viewModel::searchHistoryItemClicked,
                        viewModel::clearSearchHistory,
                    )
                }
            }
        }
    }
}
