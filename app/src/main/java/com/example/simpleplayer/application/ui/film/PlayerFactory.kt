package com.example.simpleplayer.application.ui.film

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simpleplayer.interactor.interfaces.FilmInteractor
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class PlayerFactory @Inject constructor(
    val app: Application,
    val filmInteractor: FilmInteractor
) : ViewModelProvider.AndroidViewModelFactory(app) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlayerViewModel(app, filmInteractor) as T
    }
}