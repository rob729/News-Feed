package com.rob729.newsfeed.utils

import androidx.compose.ui.unit.dp
import com.rob729.newsfeed.model.ui.NewsSourceUiData

object Constants {
    const val BASE_URL = "https://newsapi.org/v2/"
    const val NOTIFICATION_TITLE = "notification_title"
    const val NOTIFICATION_MESSAGE = "notification_message"
    const val FAB_TITLE = "News Sources"
    const val CHROME_PACKAGE_NAME = "com.android.chrome"
    val BOTTOM_BAR_HEIGHT = 70.dp
    val newsSourceUiDataLists = listOf(
        NewsSourceUiData("theverge.com", "https://kahoot.com/files/2020/10/the-verge-logo.jpg"),
        NewsSourceUiData(
            "wired.com",
            "https://res-2.cloudinary.com/crunchbase-production/image/upload/c_lpad,f_auto,q_auto:eco/v1489030150/i1jbqbfetqzi8dr9nmvb.png"
        ),
        NewsSourceUiData(
            "techcrunch.com",
            "https://pbs.twimg.com/profile_images/1096066608034918401/m8wnTWsX.png"
        ),
        NewsSourceUiData(
            "wsj.com",
            "https://kairosaerospace.com/wp-content/uploads/2020/02/wsj-social-share.png"
        ),
        NewsSourceUiData(
            "espncricinfo.com",
            "https://images-na.ssl-images-amazon.com/images/I/21h-OE4-X7L._SY355_.png"
        ),
        NewsSourceUiData(
            "thenextweb.com",
            "https://assets.stickpng.com/thumbs/5841a001a6515b1e0ad75a6e.png"
        ),
        NewsSourceUiData(
            "newscientist.com",
            "https://is5-ssl.mzstatic.com/image/thumb/Purple114/v4/f1/5a/51/f15a5132-65b6-6966-b50a-45be69857b33/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/1200x630wa.png"
        ),
        NewsSourceUiData(
            "bbc.com",
            "https://i2-prod.mirror.co.uk/incoming/article83771.ece/ALTERNATES/s1200d/bbc-logo-21217808.jpg"
        )
    )
}