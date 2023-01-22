package com.rob729.newsfeed.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun NewsSourcePill(
    imageUrl: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .background(if (isSelected) Color.Gray else Color.Transparent)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .height(75.dp)
                    .width(75.dp)
            )
        }

//        AnimatedVisibility(visible = isSelected) {
//            Spacer(
//                modifier = Modifier
//                    .height(6.dp)
//                    .background(Color.Green)
//                    .fillMaxWidth()
//            )
//        }
//        Row(modifier = Modifier.background(if (isSelected) Color.Gray else Color.Transparent)) {
//            Spacer(modifier = Modifier.width(6.dp))
//
//            Spacer(modifier = Modifier.width(6.dp))
//        }
    }
}
