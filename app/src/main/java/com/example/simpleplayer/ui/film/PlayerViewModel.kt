package com.example.simpleplayer.ui.film

import android.app.Application
import android.content.UriMatcher
import android.net.Uri
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
import com.tonyodev.fetch2.Fetch
import com.tonyodev.fetch2.NetworkType
import com.tonyodev.fetch2.Priority
import com.tonyodev.fetch2.Request
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

        val folder = File("${Environment.getExternalStorageDirectory()}/SimplePlayer")
        if (!folder.exists()) folder.mkdir()

        val filmFileLink = Uri.parse(folder.path + film.title + ".mp4")


        val request = Request(film.filmURL.toString(), filmFileLink)
        request.priority = Priority.HIGH
        request.networkType = NetworkType.ALL
        request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG")

        if (viewingState == app.getString(R.string.online_text)) {
            interactor.updateFilmModel(film.changeOfflineViewingState(true).changeFilmFileLink(filmFileLink))
            liveData.value = Response.SetViewingText(app.getString(R.string.offline_text))
        } else {
            interactor.updateFilmModel(film.changeOfflineViewingState(false))
            liveData.value = Response.SetViewingText(app.getString(R.string.online_text))
        }
    }
}