package com.example.simpleplayer.ui.main

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Adapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.simpleplayer.R
import com.example.simpleplayer.interactor.interfaces.MainInteractor
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.ui.main.adapter.FilmListAdapter
import com.example.simpleplayer.ui.main.adapter.ItemFilmHolder

class MainViewModel(
    val app: Application,
    val interactor: MainInteractor
) : AndroidViewModel(app), ItemFilmHolder.ItemFilmClickListener {

    val liveData = MutableLiveData<Response>()
    val listAdapter = FilmListAdapter(this)


    sealed class Response {
        class Success() : Response()
        class Error(val errorMsg: String) : Response()
    }


    override fun onItemFilmClick(film: Film) {

    }

    @SuppressLint("CheckResult")
    fun setupDataOnView() {
        interactor.getFilmList().subscribe({
            when (it) {
                is MainInteractor.Response.Success ->
                    listAdapter.update(it.filmList)
                is MainInteractor.Response.Error ->
                    liveData.value = Response.Error(app.getString(it.errorMsgId))
            }
        }, {it.printStackTrace()
            liveData.value = Response.Error(app.getString(R.string.error_interactor_get_data))
        })
    }
}