package com.rob729.newsfeed.model.ui

import java.io.Serializable

data class NewsSourceUiData(
    val domain: String, val imageUrl: String, val name: String
) : Serializable
