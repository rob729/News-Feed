package com.rob729.newsfeed.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rob729.newsfeed.ui.theme.lexendDecaFontFamily

@Composable
fun SearchHistoryKeywordPill(
    searchHistoryQuery: String,
    onSearchHistoryPillClick: (String) -> Unit
) {
    Box(modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)) {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .clickable { onSearchHistoryPillClick(searchHistoryQuery) },
            shadowElevation = 4.dp,
            color = MaterialTheme.colorScheme.secondaryContainer
        ) {
            Text(
                text = searchHistoryQuery,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                fontFamily = lexendDecaFontFamily,
            )
        }
    }
}
