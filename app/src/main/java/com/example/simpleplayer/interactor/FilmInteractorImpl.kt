package com.example.simpleplayer.interactor

import com.example.simpleplayer.interactor.interfaces.FilmInteractor
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.repository.AppRepository
import javax.inject.Inject

class FilmInteractorImpl @Inject constructor(
    val repository: AppRepository
): FilmInteractor {

    override fun updateFilmModel(film: Film) {
        repository.updateFilmModel(film)
    }

}