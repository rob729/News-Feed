package com.rob729.newsfeed.model.state

sealed class NewsFeedSideEffect {
    data class NewsSourceClicked(val domain: String) : NewsFeedSideEffect()
    object NewsfeedItemClicked : NewsFeedSideEffect()
    object NewsSourceFabClicked : NewsFeedSideEffect()
    object ScrollToTopClicked : NewsFeedSideEffect()
}