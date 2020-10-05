package com.example.simpleplayer.ui.film

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simpleplayer.App
import com.example.simpleplayer.MainActivity
import com.example.simpleplayer.R
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.ui.main.MainFragment
import com.example.simpleplayer.utils.showToast
import kotlinx.android.synthetic.main.player_fragment.*
import javax.inject.Inject


class PlayerFragment : Fragment(), MainActivity.BeckPressedHelper {

    companion object {
        lateinit var currentFilm: Film
        private var instance: PlayerFragment? = null
        fun getInstance(film: Film) =
            instance?.apply { currentFilm = film } ?: let {
                instance = PlayerFragment()
                instance!!.apply { currentFilm = film }
            }
    }

    @Inject
    lateinit var playerFactory: PlayerFactory

    private lateinit var viewModel: PlayerViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            context.apply {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                backPressedHelper = this@PlayerFragment
                (applicationContext as App).playerViewModelComponent.inject(this@PlayerFragment)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.player_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this@PlayerFragment,
            playerFactory
        ).get(PlayerViewModel::class.java).apply {
            liveData.observe(viewLifecycleOwner, createObserver())
            cratePlayer(currentFilm)
        }

        online_offline_text.text =
            if (currentFilm.offlineViewing) getString(R.string.offline_text)
            else getString(R.string.online_text)

        offline_watching_root.setOnClickListener {
            viewModel.updateFilm(currentFilm, online_offline_text.text.toString())
        }
    }

    private fun createObserver() = Observer<PlayerViewModel.Response> { response ->
        when (response) {
            is PlayerViewModel.Response.Error ->
                showToast(response.errorMsg)
            is PlayerViewModel.Response.StartPlayer -> {
                player_film_title.text = currentFilm.title
                response.player.apply {
                    exoplayer_view.player = this
                    prepare()
                    play()
                }
            }
            is PlayerViewModel.Response.SetViewingText -> {
                online_offline_text.text = response.text
                if (response.text == getString(R.string.offline_text))
                    showToast(getString(R.string.downloading))
            }
            is PlayerViewModel.Response.DownloadSuccess ->
                showToast(getString(R.string.downloading_success))
        }
    }

    override fun onPause() {
        super.onPause()
        exoplayer_view.player?.pause()
    }

    override fun backPressed() {
        activity?.let {
            exoplayer_view.player?.stop()
            it.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.getInstance())
                .commit()
        }
    }
}