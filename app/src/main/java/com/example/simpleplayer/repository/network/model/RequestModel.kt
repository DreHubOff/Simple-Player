package com.example.simpleplayer.repository.network.model

import com.google.gson.annotations.SerializedName


data class RequestModel(
    @SerializedName("results")
    val searchResults: List<SearchResult>
)