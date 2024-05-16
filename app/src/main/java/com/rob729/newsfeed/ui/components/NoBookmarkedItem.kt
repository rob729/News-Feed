package com.rob729.newsfeed.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rob729.newsfeed.R
import com.rob729.newsfeed.ui.theme.lexendDecaFontFamily

private const val NO_BOOKMARKED_ITEM_IMAGE_MAX_WIDTH_FRACTION = 0.4f

@Composable
fun NoBookmarkedItems() {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            imageVector = Icons.Rounded.Bookmarks,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            contentDescription = "no internet",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(NO_BOOKMARKED_ITEM_IMAGE_MAX_WIDTH_FRACTION)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(8.dp))
        )

        Text(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp),
            text = context.getString(R.string.no_bookmarked_item_title),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
            fontFamily = lexendDecaFontFamily,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .wrapContentWidth()
                .padding(top = 8.dp),
            text = context.getString(R.string.no_bookmarked_item_subtitle),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Normal,
            fontFamily = lexendDecaFontFamily,
            textAlign = TextAlign.Center
        )

    }
}

@Preview
@Composable
fun NoBookmarkedItemsPreview() {
    Column {
        NoBookmarkedItems()
    }
}
