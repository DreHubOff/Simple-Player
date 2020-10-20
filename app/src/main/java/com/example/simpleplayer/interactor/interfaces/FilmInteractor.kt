package com.example.simpleplayer.interactor.interfaces

import com.example.simpleplayer.base.BaseInteractor
import com.example.simpleplayer.model.Film

interface FilmInteractor: BaseInteractor {
    fun updateFilmModel(film: Film)
}