package com.rob729.newsfeed.ui

import android.app.Application
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.rob729.newsfeed.database.NewsDBDataSource
import com.rob729.newsfeed.database.NewsDatabase
import com.rob729.newsfeed.network.NewsApi
import com.rob729.newsfeed.network.NewsApiDataSource
import com.rob729.newsfeed.network.NewsApiDataSourceImpl
import com.rob729.newsfeed.utils.Constants
import com.rob729.newsfeed.vm.NewsRepository
import com.rob729.newsfeed.vm.NewsViewModel
import com.rob729.newsfeed.workManager.NewsSourceImagePrefetch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}