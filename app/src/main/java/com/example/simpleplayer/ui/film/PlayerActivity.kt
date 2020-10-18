package com.example.simpleplayer.ui.film

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simpleplayer.App
import com.example.simpleplayer.BaseFullscreenActivity
import com.example.simpleplayer.R
import com.example.simpleplayer.model.Film
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.player_controller.*
import kotlinx.android.synthetic.main.player_controller.view.*
import javax.inject.Inject

@Suppress("DEPRECATION")
class PlayerActivity : BaseFullscreenActivity() {

    @Inject
    lateinit var playerFactory: PlayerFactory

    private lateinit var viewModel: PlayerViewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_player)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        super.fullscreenView = exoplayer_view_root

        (application as App).playerViewModelComponent.inject(this@PlayerActivity)

        viewModel = ViewModelProvider(this@PlayerActivity, playerFactory)
            .get(PlayerViewModel::class.java)
        viewModel.liveData.observe(this@PlayerActivity, createObserver())

        // setup film in view
        currentFilm?.let {
            viewModel.cratePlayer(it)
            if (it.offlineViewing) {
                setupCashingField(CASHING_STATUS_OFFLINE)
            } else {
                setupCashingField(CASHING_STATUS_DOWNLOAD)
            }
        }

        offline_watching_root.setOnClickListener {
            currentFilm?.let {
                viewModel.updateFilm(it, download_offline_text.text.toString())
            }
        }

        exoplayer_view.setOnTouchListener(delayHideTouchListener)
    }

    private fun setupCashingField(status: Int) {
        when (status) {
            CASHING_STATUS_DOWNLOAD -> {
                download_offline_text.text = getString(R.string.download_text)
                download_offline_img.setImageResource(R.drawable.ic_download)
                player_controller_root.exo_progress?.setBufferedColor(resources.getColor(R.color.buffered_color))
            }
            CASHING_STATUS_OFFLINE -> {
                download_offline_text.text = getString(R.string.offline_text)
                download_offline_img.setImageResource(R.drawable.ic_offline)
                player_controller_root.exo_progress?.setBufferedColor(resources.getColor(R.color.player_but))
            }
        }

    }

    private fun createObserver() = Observer<PlayerViewModel.Response> { response ->
        when (response) {
            is PlayerViewModel.Response.Error ->
                Toast.makeText(this, response.errorMsg, Toast.LENGTH_SHORT).show()
            is PlayerViewModel.Response.StartPlayer -> {
                player_film_title.text = currentFilm?.title ?: "Ooops"
                response.player.apply {
                    exoplayer_view.player = this
                    prepare()
                    play()
                }
            }
            is PlayerViewModel.Response.SetViewingText -> {
                download_offline_text.text = response.text
                if (response.text == getString(R.string.offline_text)) {
                    setupCashingField(CASHING_STATUS_OFFLINE)
                } else if (response.text == getString(R.string.download_text)) {
                    setupCashingField(CASHING_STATUS_DOWNLOAD)
                }
            }
            is PlayerViewModel.Response.DownloadSuccess ->
                Toast.makeText(this, R.string.downloading_success, Toast.LENGTH_SHORT).show()
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

        private const val CASHING_STATUS_DOWNLOAD = 0
        private const val CASHING_STATUS_OFFLINE = 1
    }
}