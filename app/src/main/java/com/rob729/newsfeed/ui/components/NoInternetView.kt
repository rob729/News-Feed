package com.rob729.newsfeed.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rob729.newsfeed.R
import com.rob729.newsfeed.ui.theme.lexendDecaFontFamily

@Composable
fun NoInternetView(onTryAgainClicked: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier =
                Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally),
            text = context.getString(R.string.error_message),
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Normal,
            fontFamily = lexendDecaFontFamily,
        )
        Text(
            modifier =
                Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 4.dp),
            text = context.getString(R.string.no_internet),
            fontSize = 32.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontFamily = lexendDecaFontFamily,
            textAlign = TextAlign.Center,
        )

        Image(
            painter = painterResource(id = R.drawable.no_internet),
            contentDescription = "no internet",
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp)
                    .clip(RoundedCornerShape(8.dp)),
        )

        Button(
            onClick = onTryAgainClicked,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(
                text = context.getString(R.string.try_again),
                fontWeight = FontWeight.Medium,
                fontFamily = lexendDecaFontFamily,
            )
        }
    }
}

@Preview
@Composable
fun NoInternetViewPreview() {
    NoInternetView {
    }
}
