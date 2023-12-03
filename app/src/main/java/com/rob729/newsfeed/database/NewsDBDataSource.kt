package com.rob729.newsfeed.database

import com.rob729.newsfeed.model.api.NetworkArticle
import com.rob729.newsfeed.model.database.ArticleDbData
import com.rob729.newsfeed.model.database.NewsSourceDbData
import com.rob729.newsfeed.model.mapper.mapNetworkArticleToArticleDbData

class NewsDBDataSource(private val newsDao: NewsDao) {

    suspend fun getNewsFromNewsSourceDomain(newsSourceDomain: String): List<ArticleDbData>? {
        return newsDao.getNewsArticlesFromNewsDomain(newsSourceDomain)
    }

    suspend fun setNewsForNewsSourceDomain(
        newsSourceDomain: String,
        newsArticlesList: List<NetworkArticle>,
        newsSourceFetchTimeInMillis: Long
    ) {
        newsDao.removeSavedNewsArticlesListForNews(newsSourceDomain)
        newsArticlesList.map(
            ::mapNetworkArticleToArticleDbData
        ).forEach {
            newsDao.insertNewsArticleListForNewsSource(
                NewsSourceDbData(
                    newsSourceDomain = newsSourceDomain,
                    newsArticle = it,
                    newsSourceFetchTimeInMillis = newsSourceFetchTimeInMillis
                )
            )
        }
    }

    suspend fun getNewsSourceFetchTimeInMillis(newsSourceDomain: String): Long? {
        return newsDao.getNewsSourceDomainFetchTimeInMillis(newsSourceDomain)
    }

}
