package com.rob729.newsfeed.model.state.bookmarkedArticles

sealed class BookmarkedArticleSideEffect {
    data class BookmarkedArticleClicked(
        val selectedItemUrl: String,
    ) : BookmarkedArticleSideEffect()
}
