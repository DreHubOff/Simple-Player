package com.example.simpleplayer.ui.film

import android.app.Application
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.simpleplayer.App
import com.example.simpleplayer.R
import com.example.simpleplayer.application.services.DownloadService
import com.example.simpleplayer.interactor.interfaces.FilmInteractor
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.utils.changeFilmFileLink
import com.example.simpleplayer.utils.changeOfflineViewingState
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.tonyodev.fetch2.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.util.concurrent.TimeUnit

class PlayerViewModel(
    val app: Application,
    private val mainInteractor: FilmInteractor,
) : AndroidViewModel(app) {


    private lateinit var player: ExoPlayer

    sealed class Response {
        class Error(val errorMsg: String) : Response()
        class StartPlayer(val player: ExoPlayer) : Response()
        class SetViewingText(val text: String) : Response()
        class ChangeScreenOrientation(val orientationFlag: Int) : Response()
        object DownloadSuccess : Response()
    }

    private val disposableBag = CompositeDisposable()

    val liveData = MutableLiveData<Response>()

    @JvmOverloads
    fun cratePlayer(film: Film, firstCreation: Boolean = false) {
        // If firstCreation = true we mast crate new player instance
        if (firstCreation) {
            player = (app as App).playerViewModelComponent.getPlayer()
        }
        player.setMediaItem(
            MediaItem.fromUri(
                if (film.offlineViewing && film.filmFileLink != null) film.filmFileLink
                else film.filmURL
            )
        )
        liveData.value = Response.StartPlayer(player)
    }

    @Suppress("DEPRECATION")
    fun changeOfflineWatchingSate(film: Film) {

        // Create file link
        val fileLink = "/${film.title.replace(" ", "_")}.mp4"
        val filePath = app.applicationContext.filesDir.path.toString() + fileLink

        val file = File(filePath)
        if (!file.exists()) {
            file.createNewFile()
            file.mkdirs()
        }

        if (!film.offlineViewing) {

            val intent = Intent(app.applicationContext, DownloadService::class.java)

            // Check current build sdk version and start
            // downloading at foreground service
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                app.startForegroundService(intent)
            } else {
                app.startService(intent)
            }

        } else {
            file.delete()
            mainInteractor.updateFilmModel(film.changeOfflineViewingState(false))
            liveData.value = Response.SetViewingText(app.getString(R.string.download_text))
        }
    }


    fun startScreenOrientationTimer(delayInMillis: Long) {
        disposableBag.clear()

        val timer = Single.just(ActivityInfo.SCREEN_ORIENTATION_SENSOR)
            .delay(delayInMillis, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                liveData.value = Response.ChangeScreenOrientation(it)
            }, { /** there will never be an error **/ })

        disposableBag.add(timer)
    }
}