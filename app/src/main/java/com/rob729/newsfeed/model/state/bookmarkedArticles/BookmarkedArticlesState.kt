package com.rob729.newsfeed.model.state.bookmarkedArticles

import com.rob729.newsfeed.model.ui.NewsArticleUiData

data class BookmarkedArticlesState(
    val bookmarkedArticles: List<NewsArticleUiData> = emptyList(),
    val shouldOpenLinksUsingInAppBrowser: Boolean = true,
)
