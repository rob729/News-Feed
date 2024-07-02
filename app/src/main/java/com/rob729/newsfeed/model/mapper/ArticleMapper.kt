package com.rob729.newsfeed.model.mapper

import com.rob729.newsfeed.model.api.NetworkArticle
import com.rob729.newsfeed.model.api.NewsApiResponse
import com.rob729.newsfeed.model.database.ArticleDbData
import com.rob729.newsfeed.model.database.BookmarkedNewsArticleDbData
import com.rob729.newsfeed.model.database.NewsDbEntity
import com.rob729.newsfeed.model.ui.NewsArticleUiData

fun mapNetworkArticleToArticleDbData(networkArticle: NetworkArticle): ArticleDbData {
    return ArticleDbData(
        networkArticle.title,
        networkArticle.url,
        networkArticle.imageUrl,
        networkArticle.description,
        networkArticle.publishedAt,
        networkArticle.source?.name
    )
}

fun mapArticleDbDataToNewsArticleUiData(articleDbData: ArticleDbData): NewsArticleUiData? {
    if (articleDbData.title.isNullOrBlank()
        || articleDbData.description.isNullOrBlank()
        || articleDbData.imageUrl.isNullOrBlank()
    )
        return null
    return NewsArticleUiData(
        articleDbData.title,
        articleDbData.description,
        articleDbData.imageUrl,
        articleDbData.url,
        articleDbData.publishedAt,
        articleDbData.source.orEmpty()
    )
}

fun mapNetworkArticleToNewsArticleUiData(networkArticle: NetworkArticle): NewsArticleUiData? {
    if (networkArticle.title.isNullOrBlank()
        || networkArticle.description.isNullOrBlank()
        || networkArticle.imageUrl.isNullOrBlank()
    )
        return null
    return NewsArticleUiData(
        networkArticle.title,
        networkArticle.description,
        networkArticle.imageUrl,
        networkArticle.url,
        networkArticle.publishedAt,
        networkArticle.source?.name.orEmpty()
    )
}

fun mapNewsArticleUiDataToBookmarkedNewsArticle(newsArticleUiData: NewsArticleUiData) =
    BookmarkedNewsArticleDbData(
        newsArticleUiData.url,
        newsArticleUiData.title,
        newsArticleUiData.imageUrl,
        newsArticleUiData.description,
        newsArticleUiData.publishedAt,
        newsArticleUiData.source
    )

fun mapBookmarkedNewsArticleToNewsArticleUiData(bookmarkedNewsArticleDbData: BookmarkedNewsArticleDbData) =
    NewsArticleUiData(
        bookmarkedNewsArticleDbData.title,
        bookmarkedNewsArticleDbData.description,
        bookmarkedNewsArticleDbData.imageUrl,
        bookmarkedNewsArticleDbData.url,
        bookmarkedNewsArticleDbData.publishedAt,
        bookmarkedNewsArticleDbData.source
    )

fun NewsApiResponse.mapNewsApiResponseToNewsDbEntity() =
    NewsDbEntity(
        articles = this.networkArticles.map(::mapNetworkArticleToArticleDbData),
        totalResultCount = this.totalResultCount
    )
