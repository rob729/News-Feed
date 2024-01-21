package com.rob729.newsfeed.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun SearchResultShimmer(brush: Brush) {
    Surface(
        Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp),
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row {
                Column {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(fraction = .6f)
                            .height(15.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(fraction = .6f)
                            .height(15.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(brush)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Spacer(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .aspectRatio(SEARCH_IMAGE_ASPECT_RATIO)
                        .background(brush)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .fillMaxWidth(fraction = 0.3f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(brush)
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .weight(1f)
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .fillMaxWidth(fraction = 0.2f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(brush)
                )
            }
        }

    }
}
