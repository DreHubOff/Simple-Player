package com.example.simpleplayer.repository.network

import com.example.simpleplayer.repository.network.model.SearchResult

interface Communicator {
    fun getFilmsList(): List<SearchResult>
    fun getFilmUrlByName(name: String): String?
}