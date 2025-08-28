package com.rob729.newsfeed.model.ui

import androidx.compose.ui.graphics.vector.ImageVector

data class IconData(
    val icon: ImageVector,
    val clickAction: (() -> Unit) = { },
)
