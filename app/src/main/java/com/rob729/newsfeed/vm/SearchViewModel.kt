package com.rob729.newsfeed.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rob729.newsfeed.model.NewsResource
import com.rob729.newsfeed.model.api.NetworkNews
import com.rob729.newsfeed.model.mapper.mapNetworkArticleToNewsArticleUiData
import com.rob729.newsfeed.model.state.UiStatus
import com.rob729.newsfeed.model.state.search.SearchSideEffects
import com.rob729.newsfeed.model.state.search.SearchState
import com.rob729.newsfeed.model.ui.NewsArticleUiData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class SearchViewModel(
    private val newsRepository: NewsRepository,
) : ViewModel(),
    ContainerHost<SearchState, SearchSideEffects> {
    override val container: Container<SearchState, SearchSideEffects> = container(
        SearchState()
    )

    init {
        viewModelScope.launch {
            container.stateFlow.debounce(1000).distinctUntilChangedBy { it.searchQuery }
                .collectLatest {
                    searchNewsResultsForQuery(it.searchQuery)
                }
        }
    }


    fun updateSearchQuery(query: String) = intent {
        reduce { state.copy(searchQuery = query) }
    }

    private fun searchNewsResultsForQuery(query: String) = intent {
        reduce {
            state.copy(searchQuery = query)
        }
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

    private suspend fun SimpleSyntax<SearchState, SearchSideEffects>.updateStateFromNewsResource(
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
                (newsResource.data as? NetworkNews)?.let {
                    reduce {
                        state.copy(
                            uiStatus = UiStatus.Success(it.networkArticles.mapNotNull(::mapNetworkArticleToNewsArticleUiData))
                        )
                    }
                }
            }
        }
    }
}