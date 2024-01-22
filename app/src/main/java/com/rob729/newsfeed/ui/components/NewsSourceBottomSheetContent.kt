package com.rob729.newsfeed.ui.components

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.rob729.newsfeed.utils.Constants

private const val VISIBLE_CARDS = 4.25f
private const val ITEM_SPACING = 12
@Composable
fun NewsSourceBottomSheetContent(
    onNewsSourceClicked: (String) -> Unit,
    currentSelectedNewsSource: String
) {
    val vibrator = LocalContext.current.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val newsSourceList = Constants.newsSourceUiDataLists
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val itemWidth = (screenWidthDp - (ITEM_SPACING * VISIBLE_CARDS)).div(VISIBLE_CARDS)
    val rowState = rememberLazyListState()

    LaunchedEffect(currentSelectedNewsSource, newsSourceList) {
        rowState.scrollToItem(newsSourceList.indexOfFirst { it.domain == currentSelectedNewsSource })
    }

    Box(
        modifier = Modifier.navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            LazyRow(
                modifier = Modifier
                    .testTag("news_source_list"),
                horizontalArrangement = Arrangement.spacedBy(ITEM_SPACING.dp),
                state = rowState
            ) {
                items(Constants.newsSourceUiDataLists.size,
                    { index: Int -> newsSourceList[index].domain }) { index ->
                    NewsSourcePill(
                        itemSize = itemWidth.dp,
                        imageUrl = newsSourceList[index].imageUrl,
                        isSelected = newsSourceList[index].domain == currentSelectedNewsSource
                    ) {
                        onNewsSourceClicked(newsSourceList[index].domain)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            val vibrationEffect2 =
                                VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
                            vibrator.cancel()
                            vibrator.vibrate(vibrationEffect2)
                        }
                    }
                }
            }
        }
    }
}
