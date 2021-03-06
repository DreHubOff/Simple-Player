package com.example.simpleplayer.application.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simpleplayer.interactor.interfaces.MainInteractor
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class MainFactory @Inject constructor(val app: Application, val mainInteractor: MainInteractor) :
    ViewModelProvider.AndroidViewModelFactory(app) {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(app, mainInteractor) as T
    }
}