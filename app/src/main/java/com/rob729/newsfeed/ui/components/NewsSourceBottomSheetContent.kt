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
import com.rob729.newsfeed.AppPreferences

private const val VISIBLE_CARDS = 4.25f
private const val ITEM_SPACING = 12

@Composable
fun NewsSourceBottomSheetContent(
    newsSources: List<AppPreferences.NewsSource>,
    onNewsSourceClicked: (String) -> Unit,
    currentSelectedNewsSource: String,
) {
    val vibrator = LocalContext.current.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val itemWidth = (screenWidthDp - (ITEM_SPACING * VISIBLE_CARDS)).div(VISIBLE_CARDS)
    val rowState = rememberLazyListState()

    LaunchedEffect(currentSelectedNewsSource, newsSources) {
        rowState.scrollToItem(newsSources.indexOfFirst { it.domainUrl == currentSelectedNewsSource })
    }

    Box(
        modifier = Modifier.navigationBarsPadding(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            LazyRow(
                modifier =
                    Modifier
                        .testTag("news_source_list"),
                horizontalArrangement = Arrangement.spacedBy(ITEM_SPACING.dp),
                state = rowState,
            ) {
                items(
                    newsSources.size,
                    { index: Int -> newsSources[index].domainUrl },
                ) { index ->
                    NewsSourcePill(
                        itemSize = itemWidth.dp,
                        newsSource = newsSources[index],
                        isSelected = newsSources[index].domainUrl == currentSelectedNewsSource,
                    ) {
                        onNewsSourceClicked(newsSources[index].domainUrl)
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
