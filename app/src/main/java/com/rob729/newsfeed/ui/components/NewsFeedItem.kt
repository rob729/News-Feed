package com.rob729.newsfeed.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.rob729.newsfeed.model.ui.NewsArticleUiData
import com.rob729.newsfeed.ui.theme.lexendDecaFontFamily
import com.rob729.newsfeed.utils.Constants.NEWS_FEED_ITEM_IMAGE_CROSS_FADE_DURATION
import kotlinx.datetime.Clock
import kotlinx.datetime.toInstant

@Composable
fun NewsFeedItem(
    newsArticleUiData: NewsArticleUiData,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {
    val context = LocalContext.current

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
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(newsArticleUiData.imageUrl)
                    .crossfade(true)
                    .crossfade(NEWS_FEED_ITEM_IMAGE_CROSS_FADE_DURATION)
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.DISABLED)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .padding(bottom = 4.dp)
                    .clip(RoundedCornerShape(12.dp)),
            )
            Text(
                text = newsArticleUiData.title,
                maxLines = 2,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                fontFamily = lexendDecaFontFamily,
                lineHeight = 22.sp
            )
            Text(
                text = newsArticleUiData.description,
                maxLines = 2,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                fontFamily = lexendDecaFontFamily,
                lineHeight = 18.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 12.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule, contentDescription = "time",
                    Modifier
                        .size(14.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = getHowOldIsArticle(newsArticleUiData.publishedAt),
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .align(Alignment.CenterVertically)
                )
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "share",
                    modifier
                        .size(14.dp)
                        .align(Alignment.CenterVertically)
                        .clickable { shareArticle(context, newsArticleUiData.url) })
            }
        }
    }

}

fun getHowOldIsArticle(publishedAt: String): String {
    val timeDiff = Clock.System.now() - publishedAt.toInstant()
    return if (timeDiff.inWholeDays != 0L) {
        " ${timeDiff.inWholeDays}d"
    } else if (timeDiff.inWholeHours != 0L) {
        " ${timeDiff.inWholeHours}h"
    } else {
        " ${timeDiff.inWholeMinutes}min"
    }
}

fun shareArticle(context: Context, articleUrl: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, articleUrl)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(context, shareIntent, null)
}
