package com.rob729.newsfeed.utils

import com.rob729.newsfeed.model.ui.NewsSourceUiData

object Constants {
    const val BASE_URL = "https://newsapi.org/v2/"
    const val NOTIFICATION_TITLE = "notification_title"
    const val NOTIFICATION_MESSAGE = "notification_message"
    const val HOME_TOOLBAR_TITLE = "News Feed"
    const val BOOKMARK_TOOLBAR_TITLE = "Bookmarks"
    const val SETTINGS_TOOLBAR_TITLE = "Settings"
    const val FAB_TITLE = "News Sources"
    const val CHROME_PACKAGE_NAME = "com.android.chrome"
    val newsSourceUiDataLists =
        listOf(
            NewsSourceUiData(
                "theverge.com",
                "https://kahoot.com/files/2020/10/the-verge-logo.jpg",
                "The Verge",
            ),
            NewsSourceUiData(
                "wired.com",
                "https://images.crunchbase.com/image/upload/c_pad,h_170,w_170,f_auto,b_white,q_auto:eco,dpr_1/v1489030150/i1jbqbfetqzi8dr9nmvb.png",
                "Wired",
            ),
            NewsSourceUiData(
                "androidauthority.com",
                "https://media.licdn.com/dms/image/v2/C4D0BAQHv2yDWX1-q4Q/company-logo_200_200/company-logo_200_200/0/1631341518367?e=2147483647&v=beta&t=KRCBy3eIZWsjNZAX8vxZ9www9roGNGrEM01LNvAulqA",
                "Android Authority",
            ),
            NewsSourceUiData(
                "wsj.com",
                "https://play-lh.googleusercontent.com/eksxaPfxbTVb6VTl5aj1sXLpKc_N9Z6AZ3_5Oq6JhTXmgEQza-1v58a66p_ID0phE2Zv=w480-h960-rw",
                "The wall Street Journal",
            ),
            NewsSourceUiData(
                "9to5mac.com",
                "https://media.licdn.com/dms/image/v2/D560BAQEAeFZn5-QQpw/company-logo_200_200/B56Zd49S2lG0AM-/0/1750081023889/9to5maccom_logo?e=2147483647&v=beta&t=Fa647PtyplXf5Mjz_CrG3dRQw-M7t83b1bFIGlHTecw",
                "9to5Mac",
            ),
            NewsSourceUiData(
                "newscientist.com",
                "https://is5-ssl.mzstatic.com/image/thumb/Purple114/v4/f1/5a/51/f15a5132-65b6-6966-b50a-45be69857b33/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/1200x630wa.png",
                "New Scientist",
            ),
            NewsSourceUiData(
                "bbc.com",
                "https://radiotoday.co.uk/wp-content/uploads/2021/10/bbc.png",
                "BBC News",
            ),
            NewsSourceUiData(
                "techcrunch.com",
                "https://pbs.twimg.com/profile_images/1096066608034918401/m8wnTWsX.png",
                "TechCrunch",
            ),
        )
    const val ERROR_MESSAGE_PREFIX = "Something went wrong"
    const val API_RESULT_LANGUAGE = "en"
    const val PAGE_SIZE = 15
    const val SEARCH_RESPONSE_PAGE_SIZE = 15
    const val SORT_RESULT_FILTER_PUBLISHED_AT = "publishedAt"
    const val ANIMATION_DURATION = 500
    const val MAX_CACHE_DATA_VALID_DURATION_IN_HOURS = 6
    const val SEARCH_QUERY_UPDATE_DEBOUNCE_TIME = 1000L
    const val PREFS_NAME = "prefs_name"
    const val MAX_TOOLBAR_ELEVATION = 12
    const val PAGINATION_TRIGGER_THRESHOLD = 3
    const val OVERFLOW_MENU_ITEM_SETTINGS = "Settings"
    val themes =
        listOf(
            Theme.SYSTEM to "System default",
            Theme.LIGHT to "Light",
            Theme.DARK to "Dark",
        )
}

object SettingsConstants {
    const val THEME_PREF_TITLE = "Theme"
    const val IN_APP_BROWSER_PREF_TITLE = "Use in-app browser"
    const val IN_APP_BROWSER_PREF_SUBTITLE = "When turned off, links open in your default browser"
    const val INTERFACE_SECTION_HEADER_TITLE = "Interface"
}

enum class ScreenType {
    HOME,
    SEARCH,
}
