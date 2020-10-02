package com.example.simpleplayer.model

import android.net.Uri


class FilmInfo @JvmOverloads constructor(
    title: String,
    id: Int,
    val filmURL: Uri,
    val offlineViewing: Boolean = false,
    val filmFileLink: Uri? = null
) : Film(title = title,id = id)