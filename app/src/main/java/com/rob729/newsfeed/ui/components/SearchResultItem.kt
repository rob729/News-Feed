package com.rob729.newsfeed.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.rob729.newsfeed.model.ui.NewsArticleUiData
import com.rob729.newsfeed.ui.theme.lexendDecaFontFamily
import com.rob729.newsfeed.utils.CommonUtils

const val SEARCH_IMAGE_ASPECT_RATIO = 1.7f

@Composable
fun SearchResultItem(
    newsArticleUiData: NewsArticleUiData,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1.0f,
        animationSpec = tween(durationMillis = 150), label = ""
    )

    Surface(
        modifier = modifier
            .padding(8.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onItemClick
            )
            .scale(scale),
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row {
                Text(
                    text = newsArticleUiData.title,
                    maxLines = 2,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .fillMaxWidth(fraction = 0.6f),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = lexendDecaFontFamily
                )

                AsyncImage(
                    model = CommonUtils.getImageRequestModel(
                        LocalContext.current,
                        newsArticleUiData.imageUrl
                    ),
                    contentDescription = null,
                    alignment = Alignment.CenterEnd,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(SEARCH_IMAGE_ASPECT_RATIO)
                        .clip(RoundedCornerShape(8.dp)),
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            BottomStrip(
                newsArticleUiData.source,
                newsArticleUiData.publishedAt,
                newsArticleUiData.url,
            )
        }
    }
}

@Preview
@Composable
fun PreviewSearchResultItem() {
    SearchResultItem(
        NewsArticleUiData(
            "News title",
            "News Description",
            "",
            "",
            "6 Sept 2023",
            "news source"
        )
    ) {

    }
}
