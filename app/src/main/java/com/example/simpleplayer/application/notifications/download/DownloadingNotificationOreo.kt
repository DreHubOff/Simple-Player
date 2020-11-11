package com.example.simpleplayer.application.notifications.download

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.simpleplayer.application.notifications.Constants
import com.example.simpleplayer.application.notifications.NotificationAbstract

@RequiresApi(Build.VERSION_CODES.O)
class DownloadingNotificationOreo(context: Context): NotificationAbstract(context) {

    init {
        val channel = NotificationChannel(
            Constants.DOWNLOADING_CHANNEL_ID,
            Constants.DOWNLOADING_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.apply {
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            setShowBadge(true)
            shouldVibrate()
            enableVibration(true)
        }
        nm.createNotificationChannel(channel)

    }

    override fun getNotification(downloadingFileName: String, id: Int, requestCode: Int): Notification {
        val builder = Notification.Builder(this, Constants.DOWNLOADING_CHANNEL_ID)
        val notification = prepare(builder, downloadingFileName, requestCode)
        nm.notify(id, notification)
        return notification
    }
}