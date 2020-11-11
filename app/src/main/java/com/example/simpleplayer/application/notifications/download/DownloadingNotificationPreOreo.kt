package com.example.simpleplayer.application.notifications.download

import android.app.Notification
import android.content.Context
import com.example.simpleplayer.application.notifications.NotificationAbstract

class DownloadingNotificationPreOreo(context: Context) : NotificationAbstract(context) {
    override fun getNotification(
        downloadingFileName: String,
        id: Int,
        requestCode: Int
    ): Notification {
        @Suppress("DEPRECATION")
        val builder = Notification.Builder(this)
        val notification = prepare(builder, downloadingFileName, requestCode)
        nm.notify(id, notification)
        return notification
    }
}