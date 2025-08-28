package com.rob729.newsfeed.model.state

import com.rob729.newsfeed.model.ui.NewsEntityUiData
import com.rob729.newsfeed.model.ui.PaginationData

sealed class UiStatus {
    data class Success(
        val newsEntityUiData: NewsEntityUiData,
        val paginationData: PaginationData = PaginationData(),
    ) : UiStatus()

    data object Loading : UiStatus()

    data object Error : UiStatus()

    data object EmptyScreen : UiStatus()
}
