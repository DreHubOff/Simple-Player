package com.example.simpleplayer

import android.app.Application
import com.example.simpleplayer.repository.db.FilmDataBase

class App: Application() {

    private val filmsDataBase = FilmDataBase.getInstance(this)

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }


    private fun initDagger() {
        TODO("Not yet implemented")
    }

}