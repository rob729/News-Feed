package com.rob729.newsfeed.model.state.home

import com.rob729.newsfeed.AppPreferences.NewsSource
import com.rob729.newsfeed.model.state.UiStatus

data class HomeFeedState(
    val uiStatus: UiStatus = UiStatus.Loading,
    val selectedNewsSource: String = "theverge.com",
    val showNewsSourceBottomSheet: Boolean = false,
    val newsSources: List<NewsSource> = listOf(),
    val shouldOpenLinksUsingInAppBrowser: Boolean = true
)
