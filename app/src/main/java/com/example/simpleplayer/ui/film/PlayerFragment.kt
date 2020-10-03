package com.example.simpleplayer.ui.film

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simpleplayer.App
import com.example.simpleplayer.MainActivity
import com.example.simpleplayer.R
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.ui.main.MainFragment
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
        (activity as MainActivity).let { it.backPressedHelper = this }
        (context.applicationContext as App).playerViewModelComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.player_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }
        viewModel = ViewModelProvider(this, playerFactory).get(PlayerViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner, createObserver())
        viewModel.cratePlayer(currentFilm)
        offline_watching_root.setOnClickListener {
            viewModel.updateFilm(
                currentFilm,
                online_offline_text.text.toString()
            )
        }
    }

    private fun createObserver() = Observer<PlayerViewModel.Response> { response ->
        when (response) {
            is PlayerViewModel.Response.Error ->
                Toast.makeText(
                    viewModel.app.applicationContext,
                    response.errorMsg,
                    Toast.LENGTH_SHORT
                ).show()
            is PlayerViewModel.Response.StartPlayer -> {
                player_film_title.text = currentFilm.title
                response.player.apply {
                    exoplayer_view.player = this
                    prepare()
                    play()
                }
            }
            is PlayerViewModel.Response.SetViewingText ->
                online_offline_text.text = response.text
        }
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