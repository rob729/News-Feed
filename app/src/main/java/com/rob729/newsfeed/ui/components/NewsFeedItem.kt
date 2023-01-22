package com.rob729.newsfeed.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import kotlinx.datetime.Clock
import kotlinx.datetime.toInstant

@Composable
fun NewsFeedItem(newsArticleUiData: NewsArticleUiData, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Card(
        modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            AsyncImage(
                model = newsArticleUiData.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(165.dp)
                    .padding(bottom = 4.dp)
                    .clip(RoundedCornerShape(12.dp)),
            )
            Text(
                text = newsArticleUiData.title,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = lexendDecaFontFamily
            )
            Text(
                text = newsArticleUiData.description,
                maxLines = 3,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                fontFamily = lexendDecaFontFamily
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 12.dp, bottom = 8.dp), horizontalArrangement = Arrangement.End
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