package com.rob729.newsfeed.model.state

import com.rob729.newsfeed.model.ui.NewsArticleUiData

sealed class UiStatus {
    data class Success(val news: List<NewsArticleUiData>): UiStatus()
    object Loading: UiStatus()
    object Error: UiStatus()

    object EmptyScreen: UiStatus()
}
