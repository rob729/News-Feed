package com.rob729.newsfeed.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.rob729.newsfeed.model.ui.NewsArticleUiData
import com.rob729.newsfeed.ui.theme.lexendDecaFontFamily
import com.rob729.newsfeed.utils.CommonUtils
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Composable
fun NewsFeedItem(
    modifier: Modifier = Modifier,
    newsArticleUiData: NewsArticleUiData,
    isArticleBookmarked: Boolean,
    onItemClick: () -> Unit,
    onBookmarkClick: (isBookmarked: Boolean) -> Unit
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
        Column {
            AsyncImage(
                model = CommonUtils.getImageRequestModel(
                    LocalContext.current,
                    newsArticleUiData.imageUrl
                ),
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
            BottomStrip(
                newsArticleUiData.source,
                newsArticleUiData.publishedAt,
                newsArticleUiData.url,
                isArticleBookmarked,
                onBookmarkClick,
                Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
            )
        }
    }

}

fun getHowOldIsArticle(publishedAt: String): String {
    val timeDiff = Clock.System.now() - Instant.parse(publishedAt)
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
