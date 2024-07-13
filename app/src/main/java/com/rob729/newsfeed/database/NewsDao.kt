package com.rob729.newsfeed.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rob729.newsfeed.model.database.ArticleDbData
import com.rob729.newsfeed.model.database.BookmarkedNewsArticleDbData
import com.rob729.newsfeed.model.database.NewsDbEntity
import com.rob729.newsfeed.model.database.NewsSourceDbData
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT news_entity from news_source_table where news_source_domain = :newsSourceDomain and page = :page")
    suspend fun getNewsArticlesFromNewsDomain(newsSourceDomain: String, page: Int): NewsDbEntity?

    @Query("SELECT news_source_fetch_time from news_source_table where news_source_domain = :newsSourceDomain")
    suspend fun getNewsSourceDomainFetchTimeInMillis(newsSourceDomain: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsArticleListForNewsSource(newsSourceDbData: NewsSourceDbData)

    @Query("DELETE FROM news_source_table where news_source_domain = :newsSourceDomain")
    suspend fun removeSavedNewsArticlesListForNews(newsSourceDomain: String)

    @Query("SELECT * from bookmarked_news_article")
    fun getBookmarkedNewsArticles(): Flow<List<BookmarkedNewsArticleDbData>>

    @Insert(entity = BookmarkedNewsArticleDbData::class)
    suspend fun addBookmarkedNewsArticle(bookmarkedNewsArticleDbData: BookmarkedNewsArticleDbData)

    @Query("DELETE FROM bookmarked_news_article where url = :articleUrl")
    suspend fun removeBookmarkedNewsArticle(articleUrl: String)
}
