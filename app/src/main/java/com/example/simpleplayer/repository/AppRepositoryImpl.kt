package com.example.simpleplayer.repository

import android.annotation.SuppressLint
import com.example.simpleplayer.model.FilmInfo
import com.example.simpleplayer.model.FilmItem
import com.example.simpleplayer.repository.db.FilmDataBase
import com.example.simpleplayer.repository.network.Communicator
import com.example.simpleplayer.repository.network.model.SearchResult
import com.example.simpleplayer.repository.network.service.START_POSTER_URL
import com.example.simpleplayer.utils.toFilmEntity
import com.example.simpleplayer.utils.toFilmInfo
import com.example.simpleplayer.utils.toFilmItem
import io.reactivex.Single
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    val database: FilmDataBase,
    val communicator: Communicator
) : AppRepository {

    override fun getAllItems(): Single<List<FilmItem>> {
        return database.getFilmDao().selectAll().flatMap { filmDbList ->
            if (filmDbList.isNullOrEmpty()) {

                addListToDB(communicator.getFilmsList())

                database.getFilmDao().selectAll().map { entities ->
                    entities.map {
                        it.toFilmItem()
                    }
                }
            } else {
                Single.just(filmDbList.map {
                    it.toFilmItem()
                })
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun getFilmById(id: Int): Single<FilmInfo> {
        return database.getFilmDao().getFilmById(id = id).flatMap {
            Single.just(it.toFilmInfo())
        }
    }

    private fun addListToDB(searchResults: List<SearchResult>) {
        database.getFilmDao().insertAll(
            searchResults.map { searchResult ->
                searchResult.toFilmEntity(
                    filmUrl = communicator.getFilmUrlByName(searchResult.title) ?: "",
                    startPosterUrl = START_POSTER_URL
                )
            }
        )
    }


}