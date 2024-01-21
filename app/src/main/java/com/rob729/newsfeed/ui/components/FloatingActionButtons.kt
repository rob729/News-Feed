package com.rob729.newsfeed.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rob729.newsfeed.utils.Constants

@Composable
fun NewsSourceExtendedFab(modifier: Modifier, isExpanded: Boolean, onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        text = { Text(text = Constants.FAB_TITLE, color = MaterialTheme.colorScheme.onPrimary) },
        icon = {
            Icon(
                imageVector = Icons.Default.Newspaper,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = Constants.FAB_TITLE
            )
        },
        elevation = FloatingActionButtonDefaults.elevation(8.dp),
        expanded = isExpanded,
        containerColor = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun ScrollToTopFab(modifier: Modifier, onClick: () -> Unit) {
    SmallFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = Constants.FAB_TITLE
        )
    }
}
