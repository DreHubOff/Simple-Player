package com.example.simpleplayer.model

import android.net.Uri


data class Film @JvmOverloads constructor(
    val title: String,
    val filmURL: Uri,
    val rating: Double,
    val posterUri: Uri,
    var id: Int = 0,
    val filmFileLink: Uri? = null,
    val offlineViewing: Boolean = false,
)