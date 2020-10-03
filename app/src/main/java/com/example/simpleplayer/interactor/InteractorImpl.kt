package com.example.simpleplayer.interactor

import android.annotation.SuppressLint
import com.example.simpleplayer.R
import com.example.simpleplayer.interactor.interfaces.Interactor
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.repository.AppRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class InteractorImpl @Inject constructor(val repository: AppRepository) : Interactor {

    private val errorData = Interactor.Response.Error(R.string.error_interactor_get_data)

    @SuppressLint("CheckResult")
    override fun getFilmList(): Single<Interactor.Response> {
        return repository.getAllItems()
            .doOnError { errorData }
            .map {
                if (it.isNotEmpty()) {
                    Interactor.Response.Success(it)
                } else {
                    errorData
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getFilmById(id: Int): Single<Interactor.Response> {
        return repository.getFilmById(id)
            .doOnError { errorData }
            .map {
                if (it.isNullOrEmpty() || it.size > 1) {
                    errorData
                } else {
                    Interactor.Response.Success(it)
                }
            }
    }

    override fun updateFilmModel(film: Film) {
        repository.updateFilmModel(film)
    }


}