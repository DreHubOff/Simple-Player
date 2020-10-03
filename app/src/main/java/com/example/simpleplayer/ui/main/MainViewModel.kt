package com.example.simpleplayer.ui.main

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.simpleplayer.R
import com.example.simpleplayer.interactor.interfaces.Interactor
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.ui.main.adapter.FilmListAdapter
import com.example.simpleplayer.ui.main.adapter.ItemFilmHolder

class MainViewModel(
    val app: Application,
    val interactor: Interactor
) : AndroidViewModel(app), ItemFilmHolder.ItemFilmClickListener {

    sealed class Response {
        class Error(val errorMsg: String) : Response()
        class ActionItemClick(val film: Film):Response()
    }

    val liveData = MutableLiveData<Response>()


    val listAdapter = FilmListAdapter(this)


    override fun onItemFilmClick(film: Film) {
        liveData.value = Response.ActionItemClick(film)
    }

    @SuppressLint("CheckResult")
    fun setupDataOnView() {
        interactor.getFilmList().subscribe({
            when (it) {
                is Interactor.Response.Success ->
                    listAdapter.update(it.filmList)
                is Interactor.Response.Error ->
                    liveData.value = Response.Error(app.getString(it.errorMsgId))
            }
        }, {
            it.printStackTrace()
            liveData.value =
                Response.Error(app.getString(R.string.error_interactor_get_data))
        })
    }
}