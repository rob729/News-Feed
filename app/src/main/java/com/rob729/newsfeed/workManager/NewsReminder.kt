package com.rob729.newsfeed.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rob729.newsfeed.utils.Constants
import com.rob729.newsfeed.utils.NotificationHelper

class NewsReminder(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {

    private val notificationHelper: NotificationHelper by lazy {
        NotificationHelper(context)
    }

    override fun doWork(): Result {
        notificationHelper.createNotification(
            inputData.getString(Constants.NOTIFICATION_TITLE).toString(),
            inputData.getString(Constants.NOTIFICATION_MESSAGE).toString()
        )

        notificationHelper.scheduleNotification()
        return Result.success()
    }
}
