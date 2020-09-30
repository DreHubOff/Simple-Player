package com.example.simpleplayer.interactor

import com.example.simpleplayer.interactor.interfaces.MainInteractor
import com.example.simpleplayer.model.FilmInfo
import com.example.simpleplayer.model.FilmItem
import com.example.simpleplayer.repository.AppRepository
import io.reactivex.Single
import javax.inject.Inject

class MainInteractorImpl @Inject constructor(val repository: AppRepository) : MainInteractor {

    override fun getFilmList(): Single<List<FilmItem>> {
      return repository.getAllItems()
    }

    override fun getFilmById(id: Int): Single<FilmInfo> {
       return repository.getFilmById(id)
    }

}