package com.rob729.newsfeed.model.api

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
class NetworkNews(@Json(name = "articles") val networkArticles: List<NetworkArticle>)
