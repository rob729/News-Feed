package com.rob729.newsfeed.model.state

import com.rob729.newsfeed.model.ui.NewsArticleUiData

sealed class UiStatus {
    data class Success(val news: List<NewsArticleUiData>) : UiStatus()
    data object Loading : UiStatus()
    data object Error : UiStatus()

    data object EmptyScreen : UiStatus()
}
