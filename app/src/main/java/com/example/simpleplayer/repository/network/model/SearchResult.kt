package com.example.simpleplayer.repository.network.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val rating: Double
)