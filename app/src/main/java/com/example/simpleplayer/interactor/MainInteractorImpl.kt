package com.example.simpleplayer.interactor

import com.example.simpleplayer.interactor.interfaces.MainInteractor
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.repository.AppRepository
import io.reactivex.Single
import javax.inject.Inject

class MainInteractorImpl @Inject constructor(val repository: AppRepository) : MainInteractor {

    override fun getFilmList(): Single<List<Film>> {
      return repository.getAllItems()
    }

    override fun getFilmById(id: Int): Single<Film> {
       return repository.getFilmById(id)
    }

}