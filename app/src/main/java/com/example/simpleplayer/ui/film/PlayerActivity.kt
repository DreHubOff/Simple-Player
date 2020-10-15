package com.example.simpleplayer.ui.film

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simpleplayer.App
import com.example.simpleplayer.BaseFullscreenActivity
import com.example.simpleplayer.R
import com.example.simpleplayer.model.Film
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.activity_player.offline_watching_root
import kotlinx.android.synthetic.main.activity_player.online_offline_text
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

        (application as App).playerViewModelComponent.inject(this@PlayerActivity)

        viewModel = ViewModelProvider(
            this@PlayerActivity,
            playerFactory
        ).get(PlayerViewModel::class.java).apply {
            liveData.observe(this@PlayerActivity, createObserver())
            currentFilm?.let { cratePlayer(it) }
        }

        online_offline_text.text =
            if (currentFilm?.offlineViewing == true) getString(R.string.offline_text)
            else getString(R.string.online_text)

        offline_watching_root.setOnClickListener {
            currentFilm?.let {
                viewModel.updateFilm(it, online_offline_text.text.toString())
            }
        }

        exoplayer_view.setOnTouchListener(delayHideTouchListener)
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
                online_offline_text.text = response.text
                if (response.text == getString(R.string.offline_text))
                    Toast.makeText(this, R.string.downloading, Toast.LENGTH_SHORT).show()
            }
            is PlayerViewModel.Response.DownloadSuccess ->
                Toast.makeText(this, R.string.downloading_success, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        exoplayer_view.player?.pause()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        exoplayer_view.player?.stop()
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