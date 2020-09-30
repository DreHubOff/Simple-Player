package com.example.simpleplayer.model

import android.icu.text.CaseMap

class FilmInfo(
    title: String,
    val filmURL: String,
    val offlineViewing: Boolean = false,
    val filmFileLink: String? = null
) : Film(title)