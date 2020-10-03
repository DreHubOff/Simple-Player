package com.example.simpleplayer.ui.film

import android.app.Application
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.simpleplayer.R
import com.example.simpleplayer.interactor.interfaces.Interactor
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.utils.changeFilmFileLink
import com.example.simpleplayer.utils.changeOfflineViewingState
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.tonyodev.fetch2.*
import java.io.File

class PlayerViewModel(
    val app: Application,
    private val interactor: Interactor,
    private val player: ExoPlayer,
    private val fetch: Fetch
) : AndroidViewModel(app) {

    sealed class Response {
        class Error(val errorMsg: String) : Response()
        class StartPlayer(val player: ExoPlayer) : Response()
        class SetViewingText(val text: String) : Response()
        class DownloadError(val errorMsg: String) : Response()
        object DownloadSuccess : Response()
    }

    val liveData = MutableLiveData<Response>()

    fun cratePlayer(film: Film) {
        player.setMediaItem(
            MediaItem.fromUri(
                if (film.offlineViewing && film.filmFileLink != null) film.filmFileLink
                else film.filmURL
            )
        )
        player.setForegroundMode(false)
        liveData.value = Response.StartPlayer(player)
    }

    @Suppress("DEPRECATION")
    fun updateFilm(film: Film, viewingState: String) {
        val fileLink = "/${film.title.replace(" ", "_")}.mp4"
        val filePath = app.applicationContext.filesDir.path.toString() + fileLink

        val file = File(filePath)
        if (!file.exists()) {
            file.createNewFile()
            file.mkdirs()
        }

        if (viewingState == app.getString(R.string.online_text)) {

            val request = Request(film.filmURL.toString(), filePath)
            request.priority = Priority.HIGH
            request.networkType = NetworkType.ALL
            request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG")


            fetch.enqueue(request, { updatedRequest: Request ->
                interactor.updateFilmModel(
                    film.changeOfflineViewingState(true)
                        .changeFilmFileLink(updatedRequest.fileUri)
                )
                liveData.value = Response.SetViewingText(app.getString(R.string.offline_text))
            }
            ) {
                it.throwable?.printStackTrace()
                liveData.value = Response.DownloadError(app.getString(R.string.download_error))
            }
            fetch.addListener(object : MyFetchListener() {
                override fun onCompleted(download: Download) {
                    liveData.value = Response.DownloadSuccess
                }
            }, true)

        } else {
            file.delete()
            interactor.updateFilmModel(film.changeOfflineViewingState(false))
            liveData.value = Response.SetViewingText(app.getString(R.string.online_text))
        }
    }
}