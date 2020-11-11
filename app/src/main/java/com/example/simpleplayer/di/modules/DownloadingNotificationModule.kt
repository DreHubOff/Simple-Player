package com.example.simpleplayer.di.modules

import android.content.Context
import android.os.Build
import com.example.simpleplayer.application.notifications.download.DownloadingNotificationOreo
import com.example.simpleplayer.application.notifications.download.DownloadingNotificationPreOreo
import dagger.Module
import dagger.Provides


@Module
class DownloadingNotificationModule {

    @Provides
    fun provideDownloadingNotification(context: Context) =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            DownloadingNotificationPreOreo(context)
        } else {
            DownloadingNotificationOreo(context)
        }
}