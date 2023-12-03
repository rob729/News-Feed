package com.rob729.newsfeed.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.rob729.newsfeed.R
import com.rob729.newsfeed.ui.NewsActivity
import com.rob729.newsfeed.workManager.NewsReminder
import java.util.Calendar
import java.util.concurrent.TimeUnit

class NotificationHelper(private val context: Context) {

    fun createNotification(title: String, message: String) {
        createNotificationChannel()
        val intent = Intent(context, NewsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
        }

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = NOTIFICATION_CHANNEL_DESCRIPTION
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleNotification() {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()
        dueDate.set(Calendar.HOUR_OF_DAY, NOTIFICATION_HOUR)
        dueDate.set(Calendar.MINUTE, NOTIFICATION_MINUTE)
        dueDate.set(Calendar.SECOND, NOTIFICATION_SECONDS)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, HOURS_IN_A_DAY)
        }
        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis

        val newsReminderWorkRequest = OneTimeWorkRequestBuilder<NewsReminder>()
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .setInputData(
                workDataOf(
                    Constants.NOTIFICATION_TITLE to context.getString(R.string.notification_title),
                    Constants.NOTIFICATION_MESSAGE to context.getString(R.string.notification_sub_title),
                )
            )
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            newsReminderWorkRequest
        )

    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "Daily News Reminder"
        private const val WORK_NAME = "DAILY_NEWS_REMINDER"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION = "News Reminder Channel Description"
        private const val NOTIFICATION_HOUR = 8
        private const val NOTIFICATION_MINUTE = 30
        private const val NOTIFICATION_SECONDS = 0
        private const val HOURS_IN_A_DAY = 24
    }
}
