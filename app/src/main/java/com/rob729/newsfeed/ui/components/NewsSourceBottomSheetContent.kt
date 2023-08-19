package com.rob729.newsfeed.ui.components

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import com.rob729.newsfeed.utils.Constants

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewsSourceBottomSheetContent(
    onNewsSourceClicked: (String) -> Unit,
    currentSelectedNewsSource: String
) {
    val vibrator = LocalContext.current.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val newsSourceList = Constants.newsSourceUiDataLists
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val visibleCards = 4.25f
    val itemSpacing = 12
    val itemWidth = (screenWidthDp - (itemSpacing * visibleCards)).div(visibleCards)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .semantics { testTagsAsResourceId = true }
    ) {
        Column {
            LazyRow(modifier = Modifier.testTag("news_source_list").padding(top = 12.dp, bottom = 6.dp), horizontalArrangement = Arrangement.spacedBy(itemSpacing.dp)) {
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