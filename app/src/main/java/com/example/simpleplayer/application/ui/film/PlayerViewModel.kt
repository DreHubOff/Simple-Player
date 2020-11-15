package com.example.simpleplayer.application.ui.film

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.simpleplayer.App
import com.example.simpleplayer.R
import com.example.simpleplayer.application.services.FILM_EXTRA
import com.example.simpleplayer.application.services.DownloadService
import com.example.simpleplayer.interactor.interfaces.FilmInteractor
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.utils.actions.PlayerViewAction
import com.example.simpleplayer.utils.extensions.getString
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.util.concurrent.TimeUnit


const val PERMISSION_STORAGE_CODE = 1001

class PlayerViewModel(
    val app: Application,
    private val mainInteractor: FilmInteractor,
) : AndroidViewModel(app) {

    private val tag = this::class.java.canonicalName

    private lateinit var player: ExoPlayer

    var currentDownloadingIntent: Intent? = null

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
        liveData.value = PlayerViewAction.StartPlayer(player)
    }

    fun changeOfflineWatchingSate(film: Film) {
        if (!film.offlineViewing) {

            val intent = Intent(app.applicationContext, DownloadService::class.java)
            intent.putExtra(FILM_EXTRA, film)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (app.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED &&
                    app.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    currentDownloadingIntent = intent
                    val permissions = arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                    )
                    liveData.value =
                        PlayerViewAction.RequestPermission(permissions, PERMISSION_STORAGE_CODE)
                } else {
                    notifyDownloadingService(intent)
                }
            } else {
                notifyDownloadingService(intent)
            }

        } else {

            val f = File(film.filmFileLink.toString())
            f.delete()
            Log.d(
                tag, """file: $f was deleted|
                |file: $f exist? - ${File(film.filmFileLink.toString()).exists()}""".trimMargin()
            )
            liveData.value = PlayerViewAction.ViewingTextChange(getString(R.string.download_text))

        }
    }


    fun startScreenOrientationTimer(delayInMillis: Long) {
        val timer = Single.just(ActivityInfo.SCREEN_ORIENTATION_SENSOR)
            .delay(delayInMillis, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                liveData.value = PlayerViewAction.ChangeConfig(it)
            }.subscribe()

        // Before start timer remove previous
        disposableBag.remove(timer)
        disposableBag.add(timer)
    }

    private fun notifyDownloadingService(intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !DownloadService.started) {
            app.startForegroundService(intent)
        } else {
            app.startService(intent)
        }
        liveData.value = PlayerViewAction.DownloadingStarted
    }

    fun startDownload() {
        if (currentDownloadingIntent != null) {
            currentDownloadingIntent?.let { notifyDownloadingService(it) }
        } else {
            liveData.value = PlayerViewAction.Error(
                errorMsg = getString(R.string.download_permission_error)
            )
        }
    }
}