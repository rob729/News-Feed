package com.rob729.newsfeed.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rob729.newsfeed.model.database.BookmarkedNewsArticleDbData
import com.rob729.newsfeed.model.database.NewsSourceDbData

@TypeConverters(DataConverter::class)
@Database(
    entities = [NewsSourceDbData::class, BookmarkedNewsArticleDbData::class],
    version = 4,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {

        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
