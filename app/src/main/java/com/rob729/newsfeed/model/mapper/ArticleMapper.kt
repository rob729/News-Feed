package com.rob729.newsfeed.model.mapper

import com.rob729.newsfeed.model.api.NetworkArticle
import com.rob729.newsfeed.model.database.ArticleDbData
import com.rob729.newsfeed.model.ui.NewsArticleUiData

fun mapNetworkArticleToArticleDbData(networkArticle: NetworkArticle): ArticleDbData {
    return ArticleDbData(
        networkArticle.title,
        networkArticle.url,
        networkArticle.imageUrl,
        networkArticle.description,
        networkArticle.publishedAt
    )
}

fun mapArticleDbDataToNewsArticleUiData(articleDbData: ArticleDbData): NewsArticleUiData? {
    if (articleDbData.title.isNullOrBlank() || articleDbData.description.isNullOrBlank() || articleDbData.imageUrl.isNullOrBlank())
        return null
    return NewsArticleUiData(
        articleDbData.title,
        articleDbData.description,
        articleDbData.imageUrl,
        articleDbData.url,
        articleDbData.publishedAt
    )
}