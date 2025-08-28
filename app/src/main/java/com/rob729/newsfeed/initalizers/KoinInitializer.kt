package com.rob729.newsfeed.initalizers

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.startup.Initializer
import com.pluto.plugins.network.okhttp.addPlutoOkhttpInterceptor
import com.rob729.newsfeed.database.NewsDBDataSource
import com.rob729.newsfeed.database.NewsDatabase
import com.rob729.newsfeed.network.NewsApi
import com.rob729.newsfeed.network.NewsApiDataSource
import com.rob729.newsfeed.network.NewsApiDataSourceImpl
import com.rob729.newsfeed.repository.AppPreferencesSerializer
import com.rob729.newsfeed.repository.NewsRepository
import com.rob729.newsfeed.repository.PreferenceRepository
import com.rob729.newsfeed.utils.Constants
import com.rob729.newsfeed.utils.SearchHistoryHelper
import com.rob729.newsfeed.vm.BookmarkedArticlesVM
import com.rob729.newsfeed.vm.HomeViewModel
import com.rob729.newsfeed.vm.SearchViewModel
import com.rob729.newsfeed.vm.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class KoinInitializer : Initializer<KoinApplication> {
    override fun create(context: Context): KoinApplication =
        startKoin {
            androidContext(context)
            modules(
                module {
                    single {
                        val newsDatabase = NewsDatabase.getDatabase(context)
                        newsDatabase.newsDao()
                    }
                },
                module {
                    single {
                        val loggingInterceptor =
                            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        val okHttpClient =
                            OkHttpClient
                                .Builder()
                                .addInterceptor(loggingInterceptor)
                                .also { it.addPlutoOkhttpInterceptor() }
                                .build()

                        val retrofitInstance =
                            Retrofit
                                .Builder()
                                .baseUrl(Constants.BASE_URL)
                                .addConverterFactory(MoshiConverterFactory.create())
                                .client(okHttpClient)
                                .build()

                        retrofitInstance.create(NewsApi::class.java)
                    }
                },
                module {
                    single {
                        val dataStore =
                            PreferenceDataStoreFactory.create(produceFile = {
                                context.preferencesDataStoreFile(Constants.PREFS_NAME)
                            })
                        SearchHistoryHelper(dataStore)
                    }
                    single {
                        DataStoreFactory.create(
                            serializer = AppPreferencesSerializer,
                            produceFile = { context.dataStoreFile("app_pref.pb") },
                            corruptionHandler = null,
                            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
                        )
                    }
                },
                module {
                    singleOf(::NewsApiDataSourceImpl) { bind<NewsApiDataSource>() }
                    singleOf(::NewsDBDataSource)
                    singleOf(::NewsRepository)
                    singleOf(::PreferenceRepository)
                    viewModelOf(::HomeViewModel)
                    viewModelOf(::SearchViewModel)
                    viewModelOf(::BookmarkedArticlesVM)
                    viewModelOf(::SettingsViewModel)
                },
            )
        }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}
