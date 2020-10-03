package com.example.simpleplayer.repository

import com.example.simpleplayer.model.Film
import io.reactivex.Single

interface AppRepository {
    fun getAllItems(): Single<List<Film>>
    fun getFilmById(id: Int): Single<List<Film>>
    fun updateFilmModel(film: Film)
}