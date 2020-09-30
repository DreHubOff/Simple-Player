package com.example.simpleplayer.model

import android.net.Uri

class FilmItem(
    title: String,
    val posterUri: Uri
): Film(title)