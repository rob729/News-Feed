package com.rob729.newsfeed.initalizers

import android.content.Context
import androidx.startup.Initializer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkManagerInitializer
import com.rob729.newsfeed.workManager.NewsSourceImagePrefetch

class NewsSourceImagesPrefetch : Initializer<Unit> {
    override fun create(context: Context) {
        val newsSourceImagePreloadRequest = OneTimeWorkRequestBuilder<NewsSourceImagePrefetch>()
            .build()
        WorkManager.getInstance(context).enqueue(newsSourceImagePreloadRequest)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            WorkManagerInitializer::class.java
        )
    }
}
