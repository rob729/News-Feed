package com.rob729.newsfeed.model.ui

import java.io.Serializable

data class PaginationData(
    val showPaginationLoader: Boolean = false,
    val hasPaginationEnded: Boolean = false,
) : Serializable {
    fun shouldTriggerPagination() = !showPaginationLoader && !hasPaginationEnded
}
