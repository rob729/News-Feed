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
            "https://scontent.fdel1-3.fna.fbcdn.net/v/t39.30808-6/278026543_10158794281932291_4945991951311318753_n.jpg?_nc_cat=1&ccb=1-7&_nc_sid=09cbfe&_nc_ohc=_99-VbSrv0kAX_Fc-1X&_nc_ht=scontent.fdel1-3.fna&oh=00_AfDas8tAwkB5kGQyTKZlrd3fGSZfsm_y7cRK8S76uAFNMw&oe=641A9F67"
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
}