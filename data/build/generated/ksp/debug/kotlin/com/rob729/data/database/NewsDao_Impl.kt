package com.rob729.`data`.database

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.rob729.`data`.models.database.BookmarkedNewsArticleDbData
import com.rob729.`data`.models.database.NewsDbEntity
import com.rob729.`data`.models.database.NewsSourceDbData
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class NewsDao_Impl(
  __db: RoomDatabase,
) : NewsDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfNewsSourceDbData: EntityInsertAdapter<NewsSourceDbData>

  private val __dataConverter: DataConverter = DataConverter()

  private val __insertAdapterOfBookmarkedNewsArticleDbData:
      EntityInsertAdapter<BookmarkedNewsArticleDbData>
  init {
    this.__db = __db
    this.__insertAdapterOfNewsSourceDbData = object : EntityInsertAdapter<NewsSourceDbData>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `news_source_table` (`id`,`news_source_domain`,`news_entity`,`news_source_fetch_time`,`page`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: NewsSourceDbData) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.newsSourceDomain)
        val _tmp: String = __dataConverter.toJson(entity.newsDbEntity)
        statement.bindText(3, _tmp)
        statement.bindLong(4, entity.newsSourceFetchTimeInMillis)
        statement.bindLong(5, entity.page.toLong())
      }
    }
    this.__insertAdapterOfBookmarkedNewsArticleDbData = object : EntityInsertAdapter<BookmarkedNewsArticleDbData>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `bookmarked_news_article` (`url`,`title`,`urlToImage`,`description`,`publishedAt`,`source`) VALUES (?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: BookmarkedNewsArticleDbData) {
        statement.bindText(1, entity.url)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.imageUrl)
        statement.bindText(4, entity.description)
        statement.bindText(5, entity.publishedAt)
        statement.bindText(6, entity.source)
      }
    }
  }

  public override suspend fun insertNewsArticleListForNewsSource(newsSourceDbData: NewsSourceDbData): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfNewsSourceDbData.insert(_connection, newsSourceDbData)
  }

  public override suspend fun addBookmarkedNewsArticle(bookmarkedNewsArticleDbData: BookmarkedNewsArticleDbData): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfBookmarkedNewsArticleDbData.insert(_connection, bookmarkedNewsArticleDbData)
  }

  public override suspend fun getNewsArticlesFromNewsDomain(newsSourceDomain: String, page: Int): NewsDbEntity? {
    val _sql: String = "SELECT news_entity from news_source_table where news_source_domain = ? and page = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, newsSourceDomain)
        _argIndex = 2
        _stmt.bindLong(_argIndex, page.toLong())
        val _result: NewsDbEntity?
        if (_stmt.step()) {
          val _tmp: String?
          if (_stmt.isNull(0)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(0)
          }
          if (_tmp == null) {
            _result = null
          } else {
            _result = __dataConverter.toNewsDbEntity(_tmp)
          }
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getNewsSourceDomainFetchTimeInMillis(newsSourceDomain: String): Long? {
    val _sql: String = "SELECT news_source_fetch_time from news_source_table where news_source_domain = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, newsSourceDomain)
        val _result: Long?
        if (_stmt.step()) {
          if (_stmt.isNull(0)) {
            _result = null
          } else {
            _result = _stmt.getLong(0)
          }
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getBookmarkedNewsArticles(): Flow<List<BookmarkedNewsArticleDbData>> {
    val _sql: String = "SELECT * from bookmarked_news_article"
    return createFlow(__db, false, arrayOf("bookmarked_news_article")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "urlToImage")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfPublishedAt: Int = getColumnIndexOrThrow(_stmt, "publishedAt")
        val _columnIndexOfSource: Int = getColumnIndexOrThrow(_stmt, "source")
        val _result: MutableList<BookmarkedNewsArticleDbData> = mutableListOf()
        while (_stmt.step()) {
          val _item: BookmarkedNewsArticleDbData
          val _tmpUrl: String
          _tmpUrl = _stmt.getText(_columnIndexOfUrl)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpImageUrl: String
          _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpPublishedAt: String
          _tmpPublishedAt = _stmt.getText(_columnIndexOfPublishedAt)
          val _tmpSource: String
          _tmpSource = _stmt.getText(_columnIndexOfSource)
          _item = BookmarkedNewsArticleDbData(_tmpUrl,_tmpTitle,_tmpImageUrl,_tmpDescription,_tmpPublishedAt,_tmpSource)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun removeSavedNewsArticlesListForNews(newsSourceDomain: String) {
    val _sql: String = "DELETE FROM news_source_table where news_source_domain = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, newsSourceDomain)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun removeBookmarkedNewsArticle(articleUrl: String) {
    val _sql: String = "DELETE FROM bookmarked_news_article where url = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, articleUrl)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
