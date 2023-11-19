package com.rob729.newsfeed.model.state.search

sealed class SearchSideEffects {
    data class SearchResultClicked(val selectedResultUrl: String) : SearchSideEffects()

    data class SearchQueryChanged(val query: String) : SearchSideEffects()
}
