package com.example.simpleplayer.application.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.tonyodev.fetch2.*
import javax.inject.Inject

const val FILE_LINK_EXTRA = "DownloadService.FILE_LINK_EXTRA"
const val DOWNLOAD_LINK_EXTRA = "DownloadService.DOWNLOAD_LINK_EXTRA"

class DownloadService : Service() {

    @Inject
    lateinit var fetch: Fetch

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


//        val request = Request(film.filmURL.toString(), filePath)
//        request.priority = Priority.HIGH
//        request.networkType = NetworkType.ALL
//        request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG")
//
//
//        fetch.enqueue(request, { updatedRequest: Request ->
//            mainInteractor.updateFilmModel(
//                film.changeOfflineViewingState(true)
//                    .changeFilmFileLink(updatedRequest.fileUri)
//            )
//            liveData.value = PlayerViewModel.Response.SetViewingText(app.getString(R.string.offline_text))
//        }
//        ) {
//            it.throwable?.printStackTrace()
//            liveData.value = PlayerViewModel.Response.Error(app.getString(R.string.download_error))
//        }
//        fetch.addListener(object : MyFetchListener() {
//            override fun onCompleted(download: Download) {
//                liveData.value = PlayerViewModel.Response.DownloadSuccess
//            }
//        }, true)


        return START_STICKY
    }
}
