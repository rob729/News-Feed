package com.rob729.newsfeed.model.state

import com.rob729.newsfeed.model.ui.NewsArticleUiData

data class NewsFeedState(
    val news: List<NewsArticleUiData> = listOf(),
    val isLoading: Boolean = true,
    val selectedNewsSource: String = "theverge.com",
    val selectedNewsUrl: String = "",
    val showNewsSourceBottomSheet: Boolean = false
)
