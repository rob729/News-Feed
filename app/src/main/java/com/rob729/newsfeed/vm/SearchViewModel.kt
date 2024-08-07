package com.rob729.newsfeed.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rob729.newsfeed.model.NewsResource
import com.rob729.newsfeed.model.api.NewsApiResponse
import com.rob729.newsfeed.model.mapper.mapNetworkArticleToNewsArticleUiData
import com.rob729.newsfeed.model.state.UiStatus
import com.rob729.newsfeed.model.state.search.SearchSideEffects
import com.rob729.newsfeed.model.state.search.SearchState
import com.rob729.newsfeed.model.ui.NewsArticleUiData
import com.rob729.newsfeed.model.ui.NewsEntityUiData
import com.rob729.newsfeed.repository.NewsRepository
import com.rob729.newsfeed.repository.PreferenceRepository
import com.rob729.newsfeed.utils.Constants.SEARCH_QUERY_UPDATE_DEBOUNCE_TIME
import com.rob729.newsfeed.utils.SearchHistoryHelper
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.Syntax
import org.orbitmvi.orbit.viewmodel.container

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val newsRepository: NewsRepository,
    private val searchHistoryHelper: SearchHistoryHelper,
    private val preferenceRepository: PreferenceRepository
) : ViewModel(),
    ContainerHost<SearchState, SearchSideEffects> {
    override val container: Container<SearchState, SearchSideEffects> = container(
        SearchState()
    )

    init {
        viewModelScope.launch {
            container.stateFlow
                .debounce(SEARCH_QUERY_UPDATE_DEBOUNCE_TIME)
                .distinctUntilChangedBy { it.editTextInput }.collectLatest {
                    if (it.editTextInput != it.searchQuery) {
                        searchNewsResultsForQuery(it.editTextInput)
                    }
                }
        }

        viewModelScope.launch {
            searchHistoryHelper.searchHistoryFlow.collectLatest { searchHistorySet ->
                intent {
                    reduce { state.copy(searchHistoryList = searchHistorySet?.toList().orEmpty()) }
                }
            }
        }

        intent {
            preferenceRepository.shouldOpenLinksUsingInAppBrowser().collectLatest {
                this.reduce {
                    state.copy(shouldOpenLinksUsingInAppBrowser = it)
                }
            }
        }
    }


    fun updateSearchQuery(query: String) = intent {
        reduce { state.copy(editTextInput = query) }
    }

    private fun searchNewsResultsForQuery(query: String) = intent {
        reduce {
            state.copy(searchQuery = query, editTextInput = query)
        }
        postSideEffect(SearchSideEffects.SearchQueryChanged(query))
        if (query.isBlank()) {
            reduce { state.copy(uiStatus = UiStatus.EmptyScreen) }
        } else {
            newsRepository.getSearchResults(query).collectLatest {
                this.updateStateFromNewsResource(it)
            }
        }
    }

    fun newsFeedItemClicked(item: NewsArticleUiData) = intent {
        postSideEffect(SearchSideEffects.SearchResultClicked(item.url))
    }

    fun searchHistoryItemClicked(searchHistoryItemText: String) =
        searchNewsResultsForQuery(searchHistoryItemText)

    fun addSearchQueryToHistoryList(query: String) {
        viewModelScope.launch {
            searchHistoryHelper.addSearchQueryToHistoryList(query.trim())
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            searchHistoryHelper.clearSearchHistory()
        }
    }

    fun newsFeedItemBookmarkClicked(newsArticleUiData: NewsArticleUiData, isBookmarked: Boolean) =
        intent {
            if (isBookmarked) {
                newsRepository.addBookmarkedNewsArticle(newsArticleUiData)
            } else {
                newsRepository.removeBookmarkedNewsArticle(newsArticleUiData.url)
            }
        }

    private suspend fun Syntax<SearchState, SearchSideEffects>.updateStateFromNewsResource(
        newsResource: NewsResource
    ) {
        when (newsResource) {
            is NewsResource.Error -> {
                reduce {
                    state.copy(uiStatus = UiStatus.Error)
                }
            }

            NewsResource.Loading -> {
                reduce {
                    state.copy(uiStatus = UiStatus.Loading)
                }
            }

            is NewsResource.Success<*> -> {
                (newsResource.data as? NewsApiResponse)?.let {
                    reduce {
                        state.copy(
                            uiStatus = UiStatus.Success(
                                NewsEntityUiData(
                                    it.networkArticles
                                        .distinctBy { it.imageUrl }
                                        .mapNotNull(::mapNetworkArticleToNewsArticleUiData),
                                    it.totalResultCount
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}
