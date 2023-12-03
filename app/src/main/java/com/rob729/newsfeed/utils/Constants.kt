package com.rob729.newsfeed.utils

import com.rob729.newsfeed.model.ui.NewsSourceUiData

object Constants {
    const val BASE_URL = "https://newsapi.org/v2/"
    const val NOTIFICATION_TITLE = "notification_title"
    const val NOTIFICATION_MESSAGE = "notification_message"
    const val TOOLBAR_TITLE = "News Feed"
    const val FAB_TITLE = "News Sources"
    const val CHROME_PACKAGE_NAME = "com.android.chrome"
    val newsSourceUiDataLists = listOf(
        NewsSourceUiData("theverge.com", "https://kahoot.com/files/2020/10/the-verge-logo.jpg"),
        NewsSourceUiData(
            "wired.com",
            "https://res-2.cloudinary.com/crunchbase-production/image/upload/c_lpad,f_auto,q_auto:eco/v1489030150/i1jbqbfetqzi8dr9nmvb.png"
        ),
        NewsSourceUiData(
            "androidauthority.com",
            "https://yt3.googleusercontent.com/_Tya4y1GTYtsEzEztJIoeHV8ZQhKZN11GyyUx3VFBNnKa_CfN8csGDhiACHfMB519iCHgDjh8ls=s900-c-k-c0x00ffffff-no-rj"
        ),
        NewsSourceUiData(
            "wsj.com",
            "https://kairosaerospace.com/wp-content/uploads/2020/02/wsj-social-share.png"
        ),
        NewsSourceUiData(
            "9to5mac.com",
            "https://yt3.googleusercontent.com/IQG90t76dAEx1ELUD60rrMJKiI5J2tzfWBgN7FnpJSwBw-CHWhDEAieKeA-DrHFuxii-xZGS_iw=s900-c-k-c0x00ffffff-no-rj"
        ),
        NewsSourceUiData(
            "newscientist.com",
            "https://is5-ssl.mzstatic.com/image/thumb/Purple114/v4/f1/5a/51/f15a5132-65b6-6966-b50a-45be69857b33/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/1200x630wa.png"
        ),
        NewsSourceUiData(
            "bbc.com",
            "https://radiotoday.co.uk/wp-content/uploads/2021/10/bbc.png"
        ),
        NewsSourceUiData(
            "techcrunch.com",
            "https://pbs.twimg.com/profile_images/1096066608034918401/m8wnTWsX.png"
        ),
    )
    const val ERROR_MESSAGE_PREFIX = "Something went wrong"
    const val API_RESULT_LANGUAGE = "en"
    const val SORT_RESULT_FILTER_PUBLISHED_AT = "publishedAt"
    const val SHIMMER_ITEM_COUNT = 4
    const val ANIMATION_DURATION = 500
    const val MAX_CACHE_DATA_VALID_DURATION_IN_HOURS = 6
    const val SEARCH_QUERY_UPDATE_DEBOUNCE_TIME = 1000L
    const val NEWS_FEED_ITEM_IMAGE_CROSS_FADE_DURATION = 200
    const val GRADIENT_START_COORDINATE = 200f
}
