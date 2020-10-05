package com.example.simpleplayer.ui.film

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simpleplayer.interactor.interfaces.FilmInteractor
import com.example.simpleplayer.interactor.interfaces.MainInteractor
import com.google.android.exoplayer2.ExoPlayer
import com.tonyodev.fetch2.Fetch
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class PlayerFactory @Inject constructor(
    val app: Application,
    val filmInteractor: FilmInteractor,
    val player: ExoPlayer,
    val fetch: Fetch
) : ViewModelProvider.AndroidViewModelFactory(app) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlayerViewModel(app, filmInteractor, player, fetch) as T
    }
}