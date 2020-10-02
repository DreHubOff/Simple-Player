package com.example.simpleplayer.repository

import android.annotation.SuppressLint
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.repository.db.FilmDataBase
import com.example.simpleplayer.repository.network.Communicator
import com.example.simpleplayer.repository.network.model.ServerRequestModel
import com.example.simpleplayer.utils.toFilm
import com.example.simpleplayer.utils.toFilmEntity
import io.reactivex.Single
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    val database: FilmDataBase,
    val communicator: Communicator
) : AppRepository {

    override fun getAllItems(): Single<List<Film>> {
        return database.getFilmDao().selectAll().flatMap { filmDbList ->
            if (filmDbList.isNullOrEmpty()) {
                addListToDB(communicator.getFilmsList())
                println("AppRepositoryImpl")
                return@flatMap database.getFilmDao().selectAll().map { entities ->
                    entities.map {
                        it.toFilm()
                    }
                }
            } else {
                return@flatMap Single.just(filmDbList.map {
                    it.toFilm()
                })
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun getFilmById(id: Int): Single<List<Film>> {
        return database.getFilmDao().getFilmById(id = id).flatMap {
            Single.just(listOf(it.toFilm()))
        }
    }

    private fun addListToDB(serverRequest: List<ServerRequestModel>) {
        println(serverRequest)
        database.getFilmDao().insertAll(serverRequest.map {
            it.toFilmEntity()
        })
    }

}


