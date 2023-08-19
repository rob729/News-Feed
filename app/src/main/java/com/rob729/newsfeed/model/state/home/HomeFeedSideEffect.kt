package com.rob729.newsfeed.model.state.home

sealed class HomeFeedSideEffect {
    data class HomeSourceClicked(val domain: String) : HomeFeedSideEffect()
    data class HomeFeedItemClicked(val selectedItemUrl: String) : HomeFeedSideEffect()
    object HomeSourceFabClicked : HomeFeedSideEffect()
    object ScrollToTopClicked : HomeFeedSideEffect()
}