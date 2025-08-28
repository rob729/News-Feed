package com.rob729.newsfeed.database

import com.rob729.newsfeed.model.api.NewsApiResponse
import com.rob729.newsfeed.model.database.BookmarkedNewsArticleDbData
import com.rob729.newsfeed.model.database.NewsDbEntity
import com.rob729.newsfeed.model.database.NewsSourceDbData
import com.rob729.newsfeed.model.mapper.mapNewsApiResponseToNewsDbEntity

class NewsDBDataSource(
    private val newsDao: NewsDao,
) {
    suspend fun getNewsFromNewsSourceDomain(
        newsSourceDomain: String,
        page: Int,
    ): NewsDbEntity? = newsDao.getNewsArticlesFromNewsDomain(newsSourceDomain, page)

    suspend fun setNewsForNewsSourceDomain(
        newsSourceDomain: String,
        newsApiResponse: NewsApiResponse,
        newsSourceFetchTimeInMillis: Long,
        page: Int,
    ) {
        newsDao.removeSavedNewsArticlesListForNews(newsSourceDomain)
        newsDao.insertNewsArticleListForNewsSource(
            NewsSourceDbData(
                newsSourceDomain = newsSourceDomain,
                newsDbEntity = newsApiResponse.mapNewsApiResponseToNewsDbEntity(),
                newsSourceFetchTimeInMillis = newsSourceFetchTimeInMillis,
                page = page,
            ),
        )
    }

    suspend fun getNewsSourceFetchTimeInMillis(newsSourceDomain: String): Long? =
        newsDao.getNewsSourceDomainFetchTimeInMillis(newsSourceDomain)

    fun getBookmarkedNewsArticles() = newsDao.getBookmarkedNewsArticles()

    suspend fun addBookmarkedArticle(bookmarkedNewsArticleDbData: BookmarkedNewsArticleDbData) =
        newsDao.addBookmarkedNewsArticle(bookmarkedNewsArticleDbData)

    suspend fun removeBookmarkedArticle(newsArticleUrl: String) = newsDao.removeBookmarkedNewsArticle(newsArticleUrl)
}
