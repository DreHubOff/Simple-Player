package com.example.simpleplayer.repository.network

import android.annotation.SuppressLint
import com.example.simpleplayer.repository.network.model.SearchResult
import com.example.simpleplayer.repository.network.model.ServerRequestModel
import com.example.simpleplayer.repository.network.service.DemoFilmApi
import com.example.simpleplayer.repository.network.service.FilmApiService
import com.example.simpleplayer.repository.network.service.START_POSTER_URL
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CommunicatorImpl @Inject constructor(
    val demoFilmApi: DemoFilmApi,
    val filmApiService: FilmApiService
) : Communicator {
    @SuppressLint("CheckResult")
    override fun getFilmsList(): List<ServerRequestModel> {
        return getDemoFilmMap().toList().map { filmDemo ->
            var searchResult: SearchResult? = null
            filmApiService.getFilmByName(filmDemo.first).subscribe({requestModel->
                searchResult = requestModel.searchResults[0]
            },{it.printStackTrace()
                /*Do nothing*/})

            return@map ServerRequestModel(
                searchResult?.title ?: "Oops some error..",
                filmDemo.second,
                searchResult?.rating ?: 0.0,
                START_POSTER_URL + searchResult?.posterPath
            )
        }
    }

    @SuppressLint("CheckResult")
    private fun getDemoFilmMap():Map<String, String> {
        var result: Map<String, String> = mutableMapOf()

        demoFilmApi.getFilmNameList().subscribe({
            println(it)
            result = it
            //return@subscribe
        }, {
            it.printStackTrace()
            /*Do nothing*/
        })
        println("success")
        return result
    }

}