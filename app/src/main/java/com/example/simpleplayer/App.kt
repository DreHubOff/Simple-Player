package com.example.simpleplayer

import android.app.Application
import com.example.simpleplayer.di.component.*
import com.example.simpleplayer.di.module.CommunicatorModule
import com.example.simpleplayer.di.module.InteractorModule
import com.example.simpleplayer.di.module.MainViewModelModule
import com.example.simpleplayer.di.module.RepositoryModule
import com.example.simpleplayer.repository.db.FilmDataBase

class App : Application() {

    private lateinit var filmsDataBase: FilmDataBase
    lateinit var mainViewModelComponent: MainViewModelComponent
    private set

    override fun onCreate() {
        super.onCreate()
        filmsDataBase = FilmDataBase.getInstance(this)
        initDagger()
    }


    private fun initDagger() {
        val communicatorComponent = DaggerCommunicatorComponent.builder()
            .communicatorModule(CommunicatorModule())
            .build()

        val repositoryComponent = DaggerRepositoryComponent.builder()
            .communicatorComponent(communicatorComponent)
            .repositoryModule(RepositoryModule(filmsDataBase))
            .build()

        val interactorComponent = DaggerInteractorComponent.builder()
            .repositoryComponent(repositoryComponent)
            .interactorModule(InteractorModule())
            .build()

        mainViewModelComponent = DaggerMainViewModelComponent.builder()
            .interactorComponent(interactorComponent)
            .mainViewModelModule(MainViewModelModule(this))
            .build()

    }

}