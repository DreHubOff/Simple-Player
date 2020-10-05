package com.example.simpleplayer.interactor.interfaces

import com.example.simpleplayer.model.Film
import io.reactivex.Single

interface MainInteractor {

    sealed class Response{
        class Success(val filmList: List<Film>): Response()
        class Error(val errorMsgId: Int): Response()
    }


    fun getFilmList(): Single<Response>
    fun getFilmById(id: Int): Single<Response>
}