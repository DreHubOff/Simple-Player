package com.example.simpleplayer.application.ui.film

import android.app.Application
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.simpleplayer.App
import com.example.simpleplayer.R
import com.example.simpleplayer.application.services.DOWNLOAD_LINK_EXTRA
import com.example.simpleplayer.application.services.DownloadService
import com.example.simpleplayer.application.services.FILE_LINK_EXTRA
import com.example.simpleplayer.interactor.interfaces.FilmInteractor
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.utils.actions.PlayerViewAction
import com.example.simpleplayer.utils.changeOfflineViewingState
import com.example.simpleplayer.utils.extensions.getFileSizeMegaBytes
import com.example.simpleplayer.utils.extensions.getOrNew
import com.example.simpleplayer.utils.extensions.getString
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
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

    private val disposableBag = CompositeDisposable()

    val liveData = MutableLiveData<PlayerViewAction>()

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
        liveData.value = PlayerViewAction.START_PLAYER(player)
    }

    @Suppress("DEPRECATION")
    fun changeOfflineWatchingSate(film: Film) {
        // Create file link
        val fileName = "/${film.title.replace(" ", "_")}.mp4"
        val filePath = app.applicationContext.filesDir.path.toString() + fileName

        val file = File(filePath).getOrNew()

        if (!film.offlineViewing) {

            val intent = Intent(app.applicationContext, DownloadService::class.java)
                .apply {
                    putExtra(DOWNLOAD_LINK_EXTRA, film.filmURL)
                    putExtra(FILE_LINK_EXTRA, file.toString())
                }

            liveData.value= PlayerViewAction.START_SERVICE(intent)
        } else {
            file.delete()
            mainInteractor.updateFilmModel(film.changeOfflineViewingState(false))
            liveData.value = PlayerViewAction.VIEWING_TEXT_CHANGE(getString(R.string.download_text))
        }
    }


    fun startScreenOrientationTimer(delayInMillis: Long) {
        disposableBag.clear()

        val timer = Single.just(ActivityInfo.SCREEN_ORIENTATION_SENSOR)
            .delay(delayInMillis, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                liveData.value = PlayerViewAction.CHANGE_CONFIG(it)
            }, { /** there will never be an error **/ })

        disposableBag.add(timer)
    }
}