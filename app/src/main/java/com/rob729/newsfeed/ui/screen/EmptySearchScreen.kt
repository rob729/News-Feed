package com.rob729.newsfeed.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rob729.newsfeed.ui.components.SearchHistoryList
import com.rob729.newsfeed.ui.theme.lexendDecaFontFamily

@Composable
fun EmptySearchScreen(
    searchHistoryList: List<String>?,
    onSearchHistoryItemClick: (String) -> Unit,
    onClearSearchHistoryClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        if (searchHistoryList.isNullOrEmpty().not()) {
            Column(modifier = Modifier.padding(start = 12.dp, top = 12.dp)) {
                Row {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "Recent searches",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = lexendDecaFontFamily,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 12.dp)
                            .clickable {
                                onClearSearchHistoryClick()
                            },
                        text = "clear",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = lexendDecaFontFamily
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                SearchHistoryList(searchHistoryList, onSearchHistoryItemClick)
            }
        }

    }
}
