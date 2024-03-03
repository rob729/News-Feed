package com.rob729.newsfeed.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout


@Composable
fun BottomStrip(
    source: String,
    publishedAt: String,
    shareUrl: String,
    isArticleBookmarked: Boolean,
    onBookmarkClick: (isBookmarked: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var isBookmarked by remember { mutableStateOf(isArticleBookmarked) }

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (sourceIcon, sourceText, publishedTimeIcon, publishedTimeText,
            shareIcon, bookmarkIcon, shareAndBookmarkSpacer) = createRefs()

        Icon(
            imageVector = Icons.Default.Web, contentDescription = "source",
            Modifier
                .size(16.dp)
                .constrainAs(sourceIcon) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            text = source,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(start = 4.dp, end = 12.dp)
                .constrainAs(sourceText) {
                    start.linkTo(sourceIcon.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        TimeIcon(
            Modifier.constrainAs(publishedTimeIcon) {
                end.linkTo(publishedTimeText.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
        Text(
            text = getHowOldIsArticle(publishedAt),
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(end = 12.dp)
                .constrainAs(publishedTimeText) {
                    end.linkTo(bookmarkIcon.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Icon(
            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
            contentDescription = "bookmark",
            Modifier
                .size(16.dp)
                .constrainAs(bookmarkIcon) {
                    end.linkTo(shareAndBookmarkSpacer.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clickable {
                    isBookmarked = isBookmarked.not()
                    onBookmarkClick(isBookmarked)
                }
        )
        Spacer(modifier = Modifier
            .width(10.dp)
            .constrainAs(shareAndBookmarkSpacer) {
                end.linkTo(shareIcon.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            })
        ShareIcon(Modifier.constrainAs(shareIcon) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }, shareUrl)
    }
}

@Composable
fun ShareIcon(modifier: Modifier = Modifier, shareUrl: String) {
    val context = LocalContext.current
    Icon(
        imageVector = Icons.Filled.Share,
        contentDescription = "share",
        modifier
            .size(16.dp)
            .clickable { shareArticle(context, shareUrl) }
    )
}

@Composable
fun TimeIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Default.Schedule, contentDescription = "time",
        modifier
            .size(16.dp)
    )
}
