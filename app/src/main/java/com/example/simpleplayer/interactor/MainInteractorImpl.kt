package com.example.simpleplayer.interactor

import android.annotation.SuppressLint
import com.example.simpleplayer.R
import com.example.simpleplayer.interactor.interfaces.MainInteractor
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.repository.AppRepository
import com.example.simpleplayer.utils.actions.MainInteractorAction
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainInteractorImpl @Inject constructor(val repository: AppRepository) : MainInteractor {

    private val errorData = MainInteractorAction.ERROR(R.string.error_interactor_get_data)

    @SuppressLint("CheckResult")
    override fun getFilmList(): Single<MainInteractorAction> {
        return repository.getAllItems()
            .doOnError { errorData }
            .map {
                if (it.isNotEmpty()) {
                    MainInteractorAction.SUCCESS(it)
                } else {
                    errorData
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getFilmById(id: Int): Single<MainInteractorAction> {
        return repository.getFilmById(id)
            .doOnError { errorData }
            .map {
                if (it.isNullOrEmpty() || it.size > 1) {
                    errorData
                } else {
                    MainInteractorAction.SUCCESS(it)
                }
            }
    }

}