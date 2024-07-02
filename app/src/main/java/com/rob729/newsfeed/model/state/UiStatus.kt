package com.rob729.newsfeed.model.state

import com.rob729.newsfeed.model.ui.NewsEntityUiData

sealed class UiStatus {
    data class Success(val newsEntityUiData: NewsEntityUiData) : UiStatus()
    data object Loading : UiStatus()
    data object Error : UiStatus()

    data object EmptyScreen : UiStatus()
}
