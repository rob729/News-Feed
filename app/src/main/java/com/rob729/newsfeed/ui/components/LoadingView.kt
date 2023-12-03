package com.rob729.newsfeed.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.rob729.newsfeed.utils.Constants.SHIMMER_ITEM_COUNT

@Composable
fun LoadingView() {
    LazyColumn {
        repeat(SHIMMER_ITEM_COUNT) {
            item {
                LoadingShimmer()
            }
        }
    }
}
