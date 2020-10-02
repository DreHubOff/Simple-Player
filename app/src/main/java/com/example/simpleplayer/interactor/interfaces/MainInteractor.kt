package com.example.simpleplayer.interactor.interfaces

import com.example.simpleplayer.model.Film
import io.reactivex.Single

interface MainInteractor {
    fun getFilmList(): Single<List<Film>>
    fun getFilmById(id: Int): Single<Film>
}