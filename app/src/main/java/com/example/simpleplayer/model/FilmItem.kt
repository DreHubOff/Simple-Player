package com.example.simpleplayer.model

import android.net.Uri

class FilmItem(
    title: String,
    id: Int,
    val rating: Double,
    val posterUri: Uri
) : Film(title = title, id = id)