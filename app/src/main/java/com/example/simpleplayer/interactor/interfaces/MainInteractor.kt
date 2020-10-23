package com.example.simpleplayer.interactor.interfaces

import com.example.simpleplayer.utils.actions.MainInteractorAction
import io.reactivex.Single

interface MainInteractor {
    fun getFilmList(): Single<MainInteractorAction>
    fun getFilmById(id: Int): Single<MainInteractorAction>
}