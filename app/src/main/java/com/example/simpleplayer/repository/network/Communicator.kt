package com.example.simpleplayer.repository.network

import com.example.simpleplayer.repository.network.model.ServerRequestModel

interface Communicator {
    fun getFilmsList(): List<ServerRequestModel>
}