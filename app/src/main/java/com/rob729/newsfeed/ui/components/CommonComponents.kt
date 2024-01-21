package com.rob729.newsfeed.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ShareIcon(modifier: Modifier = Modifier, shareUrl: String) {
    val context = LocalContext.current
    Icon(
        imageVector = Icons.Filled.Share,
        contentDescription = "share",
        modifier
            .size(14.dp)
            .clickable { shareArticle(context, shareUrl) }
    )
}

@Composable
fun TimeIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Default.Schedule, contentDescription = "time",
        modifier
            .size(14.dp)
    )
}
