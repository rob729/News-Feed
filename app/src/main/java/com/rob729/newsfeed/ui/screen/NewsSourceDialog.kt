package com.rob729.newsfeed.ui.screen

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.rob729.newsfeed.ui.components.NewsSourcePill
import com.rob729.newsfeed.utils.Constants

@Composable
fun showNewsSourceDialog(
    dismissDialog: () -> Unit,
    onNewsSourceClicked: (String) -> Unit,
    currentSelectedNewsSource: String
) {
    val vibrator = LocalContext.current.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    val newsSourceList = Constants.newsSourceUiDataLists
    Dialog(onDismissRequest = { dismissDialog() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.DarkGray,
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(8.dp)
            ) {
                Column {
                    LazyVerticalGrid(modifier = Modifier, columns = GridCells.Fixed(3)) {
                        items(
                            Constants.newsSourceUiDataLists.size,
                            { index: Int -> newsSourceList[index].domain },
                            { GridItemSpan(1) }) { index ->
                            NewsSourcePill(
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
    }
}