package com.rob729.newsfeed.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import coil3.imageLoader
import coil3.request.ImageRequest
import com.rob729.newsfeed.R
import com.rob729.newsfeed.utils.Constants

class NewsSourceImagePrefetch(
    private val context: Context,
    params: WorkerParameters,
) : Worker(context, params) {
    override fun doWork(): Result {
        Constants.newsSourceUiDataLists.forEach {
            val request =
                ImageRequest
                    .Builder(context)
                    .data(it.imageUrl)
                    .size(context.resources.getDimensionPixelSize(R.dimen.news_source_size))
                    .build()
            context.imageLoader.enqueue(request)
        }
        return Result.success()
    }
}
