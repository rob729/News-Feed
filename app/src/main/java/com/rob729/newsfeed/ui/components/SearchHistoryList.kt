package com.rob729.newsfeed.ui.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchHistoryList(
    searchHistoryList: List<String>?,
    onSearchHistoryPillClick: (String) -> Unit,
) {
    searchHistoryList?.let {
        FlowRow(modifier = Modifier.fillMaxSize()) {
            it.forEach { searchQuery ->
                SearchHistoryKeywordPill(searchHistoryQuery = searchQuery, onSearchHistoryPillClick)
            }
        }
    }
}
