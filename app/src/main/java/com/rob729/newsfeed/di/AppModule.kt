package com.rob729.newsfeed.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.pluto.plugins.network.okhttp.PlutoOkhttpInterceptor
import com.rob729.newsfeed.AppPreferences
import com.rob729.newsfeed.database.NewsDao
import com.rob729.newsfeed.database.NewsDatabase
import com.rob729.newsfeed.network.NewsApi
import com.rob729.newsfeed.network.NewsApiDataSource
import com.rob729.newsfeed.network.NewsApiDataSourceImpl
import com.rob729.newsfeed.repository.AppPreferencesSerializer
import com.rob729.newsfeed.utils.Constants
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@BindingContainer
@ContributesTo(AppScope::class)
object AppModule {

    @Provides
    @SingleIn(AppScope::class)
    fun providesOkHttpClient(): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(PlutoOkhttpInterceptor)
            .build()

    }

    @Provides
    @SingleIn(AppScope::class)
    fun providesJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    @Provides
    @SingleIn(AppScope::class)
    fun providesNewsApi(okHttpClient: OkHttpClient, json: Json): NewsApi {
        val retrofitInstance =
            Retrofit
                .Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
                .client(okHttpClient)
                .build()

        return retrofitInstance.create(NewsApi::class.java)
    }

    @Provides
    @SingleIn(AppScope::class)
    fun providesPreferencesDataStore(context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(produceFile = {
            context.preferencesDataStoreFile(Constants.PREFS_NAME)
        })

    @Provides
    @SingleIn(AppScope::class)
    fun providesAppPreferencesDataStore(context: Context): DataStore<AppPreferences> =
        DataStoreFactory.create(
            serializer = AppPreferencesSerializer,
            produceFile = { context.dataStoreFile("app_pref.pb") },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        )

    @Provides
    fun providesNewsDatabase(context: Context): NewsDatabase =
        NewsDatabase.getDatabase(context)

    @Provides
    fun providesNewsDao(newsDatabase: NewsDatabase): NewsDao = newsDatabase.newsDao()

    @Provides
    fun providesNewsDataSource(newsApi: NewsApi): NewsApiDataSource = NewsApiDataSourceImpl(newsApi)
}
