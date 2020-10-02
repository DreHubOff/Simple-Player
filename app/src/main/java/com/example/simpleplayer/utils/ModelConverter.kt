package com.example.simpleplayer.utils

import android.net.Uri
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.repository.db.entities.FilmEntity
import com.example.simpleplayer.repository.network.model.SearchResult
import com.example.simpleplayer.repository.network.model.ServerRequestModel


fun ServerRequestModel.toFilmEntity() =
    FilmEntity(
        title = title,
        rating = rating,
        posterUrl = posterUri,
        filmUrl = filmURL
    )

fun FilmEntity.toFilm() =
    Film(
        id = _ID,
        title = title,
        filmURL = Uri.parse(filmUrl),
        filmFileLink = Uri.parse(filmFileLink),
        rating = rating,
        posterUri = Uri.parse(posterUrl)
    )

