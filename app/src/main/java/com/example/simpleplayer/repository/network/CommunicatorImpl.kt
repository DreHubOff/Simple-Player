package com.example.simpleplayer.repository.network

import android.annotation.SuppressLint
import com.example.simpleplayer.model.FilmItem
import com.example.simpleplayer.repository.db.entities.FilmEntity
import com.example.simpleplayer.repository.network.model.SearchResult
import com.example.simpleplayer.repository.network.service.DemoFilmApi
import com.example.simpleplayer.repository.network.service.FilmApiService
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class CommunicatorImpl @Inject constructor(
    val demoFilmApi: DemoFilmApi,
    val filmApiService: FilmApiService
) : Communicator {
    @SuppressLint("CheckResult")
    override fun getFilmsList(): List<SearchResult> {
        val resultList = mutableListOf<SearchResult>()

        demoFilmApi.getFilmNameList().subscribe({ demoDataMap ->
            demoDataMap.keys.map { name ->
                filmApiService.getFilmByName(name).subscribe({request->
                    resultList += request.searchResults[0]
                }, {/*do nothing*/ })
            }
        }, {/*do nothing*/})
        return resultList
    }

    @SuppressLint("CheckResult")
    override fun getFilmUrlByName(name: String): String? {
        var resultUrl: String? = null
        demoFilmApi.getFilmNameList().subscribe { demoDataMap ->
            demoDataMap.forEach {
                if (it.key.toLowerCase(Locale.ROOT).contains(name))
                    resultUrl = it.value
            }
        }
        return resultUrl
    }


}