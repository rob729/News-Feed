package com.rob729.newsfeed.model.state

sealed class NewsFeedSideEffect {
    data class NewsSourceClicked(val domain: String) : NewsFeedSideEffect()
    object NewsFeedItemClicked : NewsFeedSideEffect()
    object NewsSourceFabClicked : NewsFeedSideEffect()
    object ScrollToTopClicked : NewsFeedSideEffect()
}