package com.example.simpleplayer.repository.network.model

import android.net.Uri

data class ServerRequestModel (
    val title: String,
    val filmURL: String,
    val rating: Double,
    val posterUri: String,
)