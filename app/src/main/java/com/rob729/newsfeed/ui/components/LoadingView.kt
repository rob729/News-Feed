package com.rob729.newsfeed.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable

@Composable
fun LoadingView() {
    LazyColumn {
        repeat(4) {
            item {
                LoadingShimmer()
            }
        }
    }
}