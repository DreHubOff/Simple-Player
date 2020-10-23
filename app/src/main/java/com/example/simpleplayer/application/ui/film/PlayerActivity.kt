package com.example.simpleplayer.application.ui.film

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Configuration.*
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simpleplayer.App
import com.example.simpleplayer.base.BaseFullscreenActivity
import com.example.simpleplayer.R
import com.example.simpleplayer.application.services.DownloadService
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.utils.actions.DownloadingAction
import com.example.simpleplayer.utils.actions.PlayerViewAction
import com.example.simpleplayer.utils.extensions.showToast
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.player_controller.*
import kotlinx.android.synthetic.main.player_controller.view.*
import javax.inject.Inject


private const val CASHING_STATUS_DOWNLOAD = 0
private const val CASHING_STATUS_OFFLINE = 1

private const val SCREEN_ORIENTATION_CHANGING_DELAY = 10000L

class PlayerActivity : BaseFullscreenActivity() {

    @Inject
    lateinit var playerFactory: PlayerFactory

    private lateinit var viewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_player)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup fullscreen view
        super.fullscreenView = exoplayer_view_root

        // Setup dagger field
        (application as App).playerViewModelComponent.inject(this@PlayerActivity)

        // Setup single viewModel
        viewModel = ViewModelProvider(this@PlayerActivity, playerFactory)
            .get(PlayerViewModel::class.java)

        // Create observer for live data
        viewModel.liveData.observe(this@PlayerActivity, createObserver())

        // Check first creation
        if (savedInstanceState == null) {
            // Setup film in view
            currentFilm?.let {
                viewModel.cratePlayer(film = it, firstCreation = true)
                if (it.offlineViewing) {
                    setupCashingField(CASHING_STATUS_OFFLINE)
                } else {
                    setupCashingField(CASHING_STATUS_DOWNLOAD)
                }
            }
        } else {
            currentFilm?.let {
                viewModel.cratePlayer(film = it, firstCreation = false)
            }
        }

        offline_watching_root.setOnClickListener {
            currentFilm?.let {
                viewModel.changeOfflineWatchingSate(it)
            }
        }

        fullscreen_but.setOnClickListener { changeScreenOrientation() }

        exoplayer_view.setOnTouchListener(delayHideTouchListener)
    }

    private fun changeScreenOrientation() {
        requestedOrientation = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        // Start screen orientation status delay
        viewModel.startScreenOrientationTimer(SCREEN_ORIENTATION_CHANGING_DELAY)
    }

    private fun setupCashingField(status: Int) {
        when (status) {
            CASHING_STATUS_DOWNLOAD -> {
                download_offline_text.text = getString(R.string.download_text)
                download_offline_img.setImageResource(R.drawable.ic_download)

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    player_controller_root.exo_progress?.setBufferedColor(getColor(R.color.buffered_color))
                } else {
                    @Suppress("DEPRECATION")
                    player_controller_root.exo_progress?.setBufferedColor(resources.getColor(R.color.buffered_color))
                }
            }

            CASHING_STATUS_OFFLINE -> {
                download_offline_text.text = getString(R.string.offline_text)
                download_offline_img.setImageResource(R.drawable.ic_offline)
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    player_controller_root.exo_progress?.setBufferedColor(getColor(R.color.player_but))
                } else {
                    @Suppress("DEPRECATION")
                    player_controller_root.exo_progress?.setBufferedColor(resources.getColor(R.color.player_but))
                }
            }
        }

    }

    private fun createObserver() = Observer<PlayerViewAction> { action ->
        when (action) {
            is PlayerViewAction.ERROR -> {
                showToast(action.errorMsg)
            }
            is PlayerViewAction.START_PLAYER -> {
                player_film_title.text = currentFilm?.title ?: "Ooops"
                action.player.apply {
                    exoplayer_view.player = this
                    prepare()
                    play()
                }
            }
            is PlayerViewAction.VIEWING_TEXT_CHANGE -> {
                download_offline_text.text = action.text
                if (action.text == getString(R.string.offline_text)) {
                    setupCashingField(CASHING_STATUS_OFFLINE)
                } else if (action.text == getString(R.string.download_text)) {
                    setupCashingField(CASHING_STATUS_DOWNLOAD)
                }
            }
            is PlayerViewAction.CHANGE_CONFIG -> {
                requestedOrientation = action.orientationFlag
            }
            is PlayerViewAction.START_SERVICE -> {
                // Check current build sdk version and start
                // downloading at foreground service
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                    startForegroundService(action.intent)
                } else {
                    startService(action.intent)
                }
                DownloadService.liveData.observe(this, creteDownloadingActionListener())
            }
        }
    }

    private fun creteDownloadingActionListener(): Observer<in DownloadingAction> =
        Observer<DownloadingAction> { action ->


        }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        when (newConfig.orientation) {
            ORIENTATION_LANDSCAPE -> {
                fullscreen_but.setImageResource(R.drawable.ic_fullscreen_exit)
            }
            ORIENTATION_PORTRAIT -> {
                fullscreen_but.setImageResource(R.drawable.ic_fullscreen)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        exoplayer_view.player?.pause()
    }

    companion object {
        fun startPlayerActivity(context: Context, film: Film) {
            currentFilm = film
            val intent = Intent(context, PlayerActivity::class.java)
            context.startActivity(intent)
        }

        private var currentFilm: Film? = null
    }
}