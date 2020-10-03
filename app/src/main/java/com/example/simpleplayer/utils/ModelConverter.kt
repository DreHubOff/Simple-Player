package com.example.simpleplayer.utils

import android.net.Uri
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.repository.db.entities.FilmEntity
import com.example.simpleplayer.repository.network.model.ServerRequestModel


fun ServerRequestModel.toFilmEntity() =
    FilmEntity(
        title = title,
        rating = rating,
        posterUrl = posterUri,
        filmUrl = filmURL
    )

fun FilmEntity.toFilm() : Film{
    return Film(
        id = _ID,
        title = title,
        filmURL = Uri.parse(filmUrl),
        rating = rating,
        posterUri = Uri.parse(posterUrl),
        offlineViewing = offlineViewing,
        filmFileLink = Uri.parse(filmFileLink?:"")
    )
}

fun Film.toFilmEntity() : FilmEntity{
    return FilmEntity(
        _ID = id,
        title = title,
        filmUrl = filmURL.toString(),
        rating = rating,
        posterUrl = posterUri.toString(),
        offlineViewing = offlineViewing,
        filmFileLink = filmFileLink.toString()
    )
}

fun Film.changeOfflineViewingState(state: Boolean): Film{
    return Film(title = title,
        filmURL = filmURL,
        rating = rating,
        posterUri = posterUri,
        id = id,
        offlineViewing = state,
        filmFileLink = filmFileLink
    )
}

fun Film.changeFilmFileLink(fileLink: Uri): Film{
    return Film(title = title,
        filmURL = filmURL,
        rating = rating,
        posterUri = posterUri,
        id = id,
        offlineViewing = offlineViewing,
        filmFileLink = fileLink
    )
}


