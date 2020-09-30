package com.example.simpleplayer.repository

import com.example.simpleplayer.repository.db.FilmDataBase
import com.example.simpleplayer.repository.db.entities.FilmEntity
import io.reactivex.Single
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(val database: FilmDataBase) : AppRepository {

    override fun getAllItems(): Single<List<FilmEntity>> {
        return database.getFilmDao().selectAll()
    }

    override fun getFilmById(id: Int): Single<FilmEntity> {
        return database.getFilmDao().getFilmById(id = id)
    }

}