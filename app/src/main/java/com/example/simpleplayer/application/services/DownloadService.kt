package com.example.simpleplayer.application.services

import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.example.simpleplayer.R
import com.example.simpleplayer.App
import com.example.simpleplayer.application.ui.film.MyFetchListener
import com.example.simpleplayer.utils.actions.DownloadingAction
import com.tonyodev.fetch2.*
import com.tonyodev.fetch2core.DownloadBlock
import javax.inject.Inject

const val FILE_LINK_EXTRA = "DownloadService.FILE_LINK_EXTRA"
const val DOWNLOAD_LINK_EXTRA = "DownloadService.DOWNLOAD_LINK_EXTRA"

private const val HEADER_KEY = "clientKey"
private const val HEADER_VALUE = "SD78DF93_3947&MVNGHE1WONG"

class DownloadService : LifecycleService(), MyFetchListener {

    companion object {
        @JvmStatic
        val liveData = MutableLiveData<DownloadingAction>()
        private var requestCodes = mutableListOf<Int>()
    }

    @Inject
    lateinit var fetch: Fetch
    lateinit var app: App

    override fun onCreate() {
        super.onCreate()
        app = (applicationContext as App)
        app.downloadingServiceComponent.inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        var filmUrl: String? = null
        var fileLink: String? = null

        intent?.let {
            fileLink = it.getStringExtra(FILE_LINK_EXTRA)
            filmUrl = it.getStringExtra(DOWNLOAD_LINK_EXTRA)
        }

        if (filmUrl.isNullOrEmpty() || fileLink.isNullOrEmpty()) {
            reportError()
        } else {
            fetch.enqueue(
                getFetchRequest(filmUrl!!, fileLink!!),
                { /**Do on Success**/ }
            ) {
                reportError(stopService = true)
                stopSelf(startId)
            }
            fetch.addListener(this, true)
        }
        return START_STICKY
    }

    private fun reportError(stopService: Boolean = false) {
        liveData.postValue(DownloadingAction.ERROR(getString(R.string.download_error)))
        if (stopService) {
            stopForeground(true)
        }
    }

    private fun getFetchRequest(downloadingLink: String, fileLink: String): Request {
        return Request(downloadingLink, fileLink).apply {
            networkType = NetworkType.ALL
            autoRetryMaxAttempts = 3
            priority = Priority.NORMAL
            addHeader(HEADER_KEY, HEADER_VALUE)
        }
    }

    //Fetch Listener:
    override fun onAdded(download: Download) {
        requestCodes.add(requestCodes.size + 1000)
        val currentNotification =
            app.downloadingServiceComponent.getDownloadingNotificator().getNotification(
                download.file,
                download.id,
                requestCodes.last()
            )
        startForeground(requestCodes.last(), currentNotification)
    }

    override fun onStarted(
        download: Download,
        downloadBlocks: List<DownloadBlock>,
        totalBlocks: Int
    ) {

    }

    override fun onProgress(
        download: Download,
        etaInMilliSeconds: Long,
        downloadedBytesPerSecond: Long
    ) {

    }

    override fun onCancelled(download: Download) {

    }

    override fun onError(download: Download, error: Error, throwable: Throwable?) {

    }

}
