package com.example.simpleplayer.interactor.interfaces

import com.example.simpleplayer.model.FilmInfo
import com.example.simpleplayer.model.FilmItem
import io.reactivex.Single

interface MainInteractor {
    fun getFilmList(): Single<List<FilmItem>>
    fun getFilmById(id: Int): Single<FilmInfo>
}