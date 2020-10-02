package com.example.simpleplayer.repository.network

import android.annotation.SuppressLint
import android.net.Uri
import com.example.simpleplayer.repository.network.model.SearchResult
import com.example.simpleplayer.repository.network.model.ServerRequestModel
import com.example.simpleplayer.repository.network.service.DemoFilmApi
import com.example.simpleplayer.repository.network.service.FilmApiService
import com.example.simpleplayer.repository.network.service.START_POSTER_URL
import javax.inject.Inject

class CommunicatorImpl @Inject constructor(
    val demoFilmApi: DemoFilmApi,
    val filmApiService: FilmApiService
) : Communicator {
    @SuppressLint("CheckResult")
    override fun getFilmsList(): List<ServerRequestModel> {
        return getDemoFilmMep().toList().map {filmDemo ->
            var searchResult: SearchResult? = null
            filmApiService.getFilmByName(filmDemo.first).subscribe({requestModel->
                searchResult = requestModel.searchResults[0]
            },{/*Do nothing*/})

            return@map ServerRequestModel(
                searchResult?.title ?: "Ups some error..",
                filmDemo.second,
                searchResult?.rating ?: 0.0,
                START_POSTER_URL + searchResult?.posterPath
            )
        }
    }

    @SuppressLint("CheckResult")
    private fun getDemoFilmMep():Map<String, String> {
        var resuld: Map<String, String> = mutableMapOf()
        demoFilmApi.getFilmNameList().subscribe({
            resuld = it
        },{/*Do nothing*/})
        return resuld
    }

}