package com.example.simpleplayer.repository

import com.example.simpleplayer.model.FilmInfo
import com.example.simpleplayer.model.FilmItem
import io.reactivex.Single

interface AppRepository {
    fun getAllItems(): Single<List<FilmItem>>
    fun getFilmById(id: Int): Single<FilmInfo>
}