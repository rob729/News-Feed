package com.rob729.newsfeed.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rob729.newsfeed.model.database.ArticleDbData
import com.rob729.newsfeed.model.database.NewsSourceDbData

@Dao
interface NewsDao {

    @Query("SELECT news_article from news_source_table where news_source_domain = :newsSourceDomain")
    suspend fun getNewsArticlesFromNewsDomain(newsSourceDomain: String): List<ArticleDbData>?

    @Query("SELECT news_source_fetch_time from news_source_table where news_source_domain = :newsSourceDomain")
    suspend fun getNewsSourceDomainFetchTimeInMillis(newsSourceDomain: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsArticleListForNewsSource(newsSourceDbData: NewsSourceDbData)

    @Query("DELETE FROM news_source_table where news_source_domain = :newsSourceDomain")
    suspend fun removeSavedNewsArticlesListForNews(newsSourceDomain: String)
}