package com.example.simpleplayer.application.services

import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
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

class DownloadService : LifecycleService() {

    companion object {
        @JvmStatic
        val liveData = MutableLiveData<DownloadingAction>()
    }

    @Inject
    lateinit var fetch: Fetch

    override fun onCreate() {
        super.onCreate()
        (applicationContext as App).downloadingServiceComponent.inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        var filmUrl: String? = null
        var fileLink: String? = null
        intent?.let {
            fileLink = it.getStringExtra(FILE_LINK_EXTRA)
            filmUrl = it.getStringExtra(DOWNLOAD_LINK_EXTRA)
        }

        fetch.enqueue(
            getFetchRequest(filmUrl ?: "", fileLink ?: ""),
            {
//                    updatedRequest: Request ->
//            mainInteractor.updateFilmModel(
//                film.changeOfflineViewingState(true)
//                    .changeFilmFileLink(updatedRequest.fileUri)
//            )
//            liveData.value =
//                PlayerViewModel.Response.SetViewingText(app.getString(R.string.offline_text))
            }
        ) {
            //           liveData.value = PlayerViewModel.Response.Error(app.getString(R.string.download_error))
        }

        fetch.addListener(FetchListener(), true)

        return START_STICKY
    }

    private fun getFetchRequest(downloadingLink: String, fileLink: String): Request {
        return Request(downloadingLink, fileLink).apply {
            networkType = NetworkType.ALL
            autoRetryMaxAttempts = 3
            priority = Priority.HIGH
            addHeader(HEADER_KEY, HEADER_VALUE)
        }
    }



    private class FetchListener : MyFetchListener() {

        override fun onStarted(
            download: Download,
            downloadBlocks: List<DownloadBlock>,
            totalBlocks: Int
        ) {
            super.onStarted(download, downloadBlocks, totalBlocks)
        }

        override fun onProgress(
            download: Download,
            etaInMilliSeconds: Long,
            downloadedBytesPerSecond: Long
        ) {
            super.onProgress(download, etaInMilliSeconds, downloadedBytesPerSecond)
        }

        override fun onCancelled(download: Download) {
            super.onCancelled(download)
        }

        override fun onError(download: Download, error: Error, throwable: Throwable?) {
            super.onError(download, error, throwable)
        }
    }

}
