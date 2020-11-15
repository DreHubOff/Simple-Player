package com.example.simpleplayer.application.services

import android.app.NotificationManager
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.example.simpleplayer.R
import com.example.simpleplayer.App
import com.example.simpleplayer.application.ui.film.MyFetchListener
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.utils.actions.DownloadingAction
import com.example.simpleplayer.utils.extensions.getOrNew
import com.example.simpleplayer.utils.extensions.showToast
import com.tonyodev.fetch2.*
import com.tonyodev.fetch2core.DownloadBlock
import java.io.File
import javax.inject.Inject

const val FILM_EXTRA = "DownloadService.FILM_EXTRA"

private const val HEADER_KEY = "clientKey"
private const val HEADER_VALUE = "SD78DF93_3947&MVNGHE1WONG"

class DownloadService : LifecycleService(), MyFetchListener {

    companion object {
        @JvmStatic
        val liveData = MutableLiveData<DownloadingAction>()
        @JvmStatic
        var started = false
        private set
    }

    private val teg = this::class.java.canonicalName

    @Inject
    lateinit var fetch: Fetch

    @Inject
    lateinit var notificationManage: NotificationManager

    lateinit var app: App

    override fun onCreate() {
        super.onCreate()
        app = (applicationContext as App)
        app.downloadingServiceComponent.inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        started = true

        var film: Film? = null
        intent?.let { film = it.getParcelableExtra(FILM_EXTRA) }

        if (film == null) {
            reportError()
        } else {
            //startDownload(film!!)
            startForeground()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        started = false
    }

    private fun startForeground() {
        val currentNotification =
            app.downloadingServiceComponent.getDownloadingNotificator().getNotification(
                "film!!.title",
                2222,
                120
            )
        startForeground(211, currentNotification)
    }

    private fun startDownload(film: Film) {
        // Create file link
        val fName = "/${film.title.replace(" ", "_")}.mp4"
        val fPath = app.applicationContext.filesDir.path.toString() + fName

        val file = File(fPath).getOrNew()

        val request = getFetchRequest(
            downloadingLink = film.filmURL.toString(),
            fileLink = fPath
        )
        fetch.enqueue(
            request = request,
            func = { successRequest ->
                Log.d(teg, "File: ${successRequest.file} - wos successfully added to the queue")
            },
            func2 = { error ->
                Log.d(teg, error.throwable?.localizedMessage ?: "Fetch request error")
                reportError()
            })
        fetch.addListener(this, true)
    }


    private fun reportError() {
        liveData.postValue(DownloadingAction.ERROR(getString(R.string.download_error)))
        stopForeground(true)
        stopSelf()
    }

    private fun getFetchRequest(downloadingLink: String, fileLink: String): Request {
        return Request(downloadingLink, fileLink).apply {
            networkType = NetworkType.ALL
            autoRetryMaxAttempts = 5
            priority = Priority.NORMAL
            addHeader(HEADER_KEY, HEADER_VALUE)
        }
    }

    //Fetch Listener:
    override fun onAdded(download: Download) {

    }

    override fun onStarted(
        download: Download,
        downloadBlocks: List<DownloadBlock>,
        totalBlocks: Int
    ) {
        showToast("Downloading started")
    }

    override fun onProgress(
        download: Download,
        etaInMilliSeconds: Long,
        downloadedBytesPerSecond: Long
    ) {

    }

    override fun onCancelled(download: Download) {
        showToast("Downloading cancelled")
    }

    override fun onCompleted(download: Download) {
        showToast("Downloading success")
    }

    override fun onError(download: Download, error: Error, throwable: Throwable?) {

    }

}
