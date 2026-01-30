package com.rob729.`data`.database

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class NewsDatabase_Impl : NewsDatabase() {
  private val _newsDao: Lazy<NewsDao> = lazy {
    NewsDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(4, "112730a2fe68a63ecf7a11d255977d98", "b1eace9fcfdff499b9a464539ecea223") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `news_source_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `news_source_domain` TEXT NOT NULL, `news_entity` TEXT NOT NULL, `news_source_fetch_time` INTEGER NOT NULL, `page` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `bookmarked_news_article` (`url` TEXT NOT NULL, `title` TEXT NOT NULL, `urlToImage` TEXT NOT NULL, `description` TEXT NOT NULL, `publishedAt` TEXT NOT NULL, `source` TEXT NOT NULL, PRIMARY KEY(`url`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '112730a2fe68a63ecf7a11d255977d98')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `news_source_table`")
        connection.execSQL("DROP TABLE IF EXISTS `bookmarked_news_article`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsNewsSourceTable: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsNewsSourceTable.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewsSourceTable.put("news_source_domain", TableInfo.Column("news_source_domain", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewsSourceTable.put("news_entity", TableInfo.Column("news_entity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewsSourceTable.put("news_source_fetch_time", TableInfo.Column("news_source_fetch_time", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNewsSourceTable.put("page", TableInfo.Column("page", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysNewsSourceTable: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesNewsSourceTable: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoNewsSourceTable: TableInfo = TableInfo("news_source_table", _columnsNewsSourceTable, _foreignKeysNewsSourceTable, _indicesNewsSourceTable)
        val _existingNewsSourceTable: TableInfo = read(connection, "news_source_table")
        if (!_infoNewsSourceTable.equals(_existingNewsSourceTable)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |news_source_table(com.rob729.data.models.database.NewsSourceDbData).
              | Expected:
              |""".trimMargin() + _infoNewsSourceTable + """
              |
              | Found:
              |""".trimMargin() + _existingNewsSourceTable)
        }
        val _columnsBookmarkedNewsArticle: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsBookmarkedNewsArticle.put("url", TableInfo.Column("url", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBookmarkedNewsArticle.put("title", TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBookmarkedNewsArticle.put("urlToImage", TableInfo.Column("urlToImage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBookmarkedNewsArticle.put("description", TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBookmarkedNewsArticle.put("publishedAt", TableInfo.Column("publishedAt", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBookmarkedNewsArticle.put("source", TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysBookmarkedNewsArticle: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesBookmarkedNewsArticle: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoBookmarkedNewsArticle: TableInfo = TableInfo("bookmarked_news_article", _columnsBookmarkedNewsArticle, _foreignKeysBookmarkedNewsArticle, _indicesBookmarkedNewsArticle)
        val _existingBookmarkedNewsArticle: TableInfo = read(connection, "bookmarked_news_article")
        if (!_infoBookmarkedNewsArticle.equals(_existingBookmarkedNewsArticle)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |bookmarked_news_article(com.rob729.data.models.database.BookmarkedNewsArticleDbData).
              | Expected:
              |""".trimMargin() + _infoBookmarkedNewsArticle + """
              |
              | Found:
              |""".trimMargin() + _existingBookmarkedNewsArticle)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "news_source_table", "bookmarked_news_article")
  }

  public override fun clearAllTables() {
    super.performClear(false, "news_source_table", "bookmarked_news_article")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(NewsDao::class, NewsDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    _autoMigrations.add(NewsDatabase_AutoMigration_1_2_Impl())
    return _autoMigrations
  }

  public override fun newsDao(): NewsDao = _newsDao.value
}
