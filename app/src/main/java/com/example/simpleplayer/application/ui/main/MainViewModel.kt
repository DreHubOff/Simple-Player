package com.example.simpleplayer.application.ui.main

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.simpleplayer.R
import com.example.simpleplayer.interactor.interfaces.MainInteractor
import com.example.simpleplayer.model.Film
import com.example.simpleplayer.application.adapters.main.FilmListAdapter
import com.example.simpleplayer.application.adapters.main.ItemFilmHolder
import com.example.simpleplayer.utils.actions.MainInteractorAction
import com.example.simpleplayer.utils.actions.MainViewAction
import com.example.simpleplayer.utils.extensions.getString

class MainViewModel(
    val app: Application,
    private val mainInteractor: MainInteractor
) : AndroidViewModel(app), ItemFilmHolder.ItemFilmClickListener {

    val liveData = MutableLiveData<MainViewAction>()

    val listAdapter = FilmListAdapter(this)

    override fun onItemFilmClick(film: Film) {
        liveData.value = MainViewAction.ITEM_CLICK(film)
    }

    @SuppressLint("CheckResult")
    fun setupDataOnView() {
        mainInteractor.getFilmList().subscribe({
            when (it) {
                is MainInteractorAction.SUCCESS ->
                    listAdapter.update(it.filmList)
                is MainInteractorAction.ERROR ->
                    liveData.value = MainViewAction.ERROR(getString(it.errorMsgId))
            }
        }, {
            it.printStackTrace()
            liveData.value =
                MainViewAction.ERROR(getString(R.string.error_interactor_get_data))
        })
    }
}