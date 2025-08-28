package com.rob729.newsfeed.model.state.home

sealed class HomeFeedSideEffect {
    data class NewsSourceClicked(
        val domain: String,
    ) : HomeFeedSideEffect()

    data class FeedItemClicked(
        val selectedItemUrl: String,
    ) : HomeFeedSideEffect()

    data object NewsSourceFabClicked : HomeFeedSideEffect()

    data object ScrollToTopClicked : HomeFeedSideEffect()
}
