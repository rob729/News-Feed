package com.rob729.newsfeed.model.state.search

import com.rob729.newsfeed.model.state.UiStatus

data class SearchState(
    val uiStatus: UiStatus = UiStatus.EmptyScreen,
    val editTextInput: String = "",
    val searchQuery: String = "",
    val searchHistoryList: List<String> = listOf(),
    val shouldOpenLinksUsingInAppBrowser: Boolean = true,
)
