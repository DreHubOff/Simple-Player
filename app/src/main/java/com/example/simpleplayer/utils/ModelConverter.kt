package com.example.simpleplayer.utils

import android.net.Uri
import com.example.simpleplayer.model.FilmInfo
import com.example.simpleplayer.model.FilmItem
import com.example.simpleplayer.repository.db.entities.FilmEntity
import com.example.simpleplayer.repository.network.model.SearchResult

fun FilmEntity.toFilmItem(): FilmItem {
    return FilmItem(
        title = title,
        id = _ID,
        rating = rating,
        posterUri = Uri.parse(this.posterUrl)
    )
}

fun FilmEntity.toFilmInfo(): FilmInfo {
    return FilmInfo(
        title = title,
        id = _ID,
        filmURL = Uri.parse(filmUrl),
        offlineViewing = offlineViewing,
        filmFileLink = Uri.parse(filmFileLink)
    )
}

fun SearchResult.toFilmEntity(filmUrl: String, startPosterUrl: String) =
    FilmEntity(
        title = title,
        rating = rating,
        posterUrl = startPosterUrl + this.posterPath,
        filmUrl = filmUrl
    )

