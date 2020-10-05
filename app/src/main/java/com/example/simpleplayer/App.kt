package com.example.simpleplayer

import android.app.Application
import com.example.simpleplayer.di.components.*
import com.example.simpleplayer.di.modules.*
import com.example.simpleplayer.repository.db.FilmDataBase

class App : Application() {

    private lateinit var filmsDataBase: FilmDataBase
    lateinit var mainViewModelComponent: MainViewModelComponent
    lateinit var playerViewModelComponent: PlayerViewModelComponent
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
            .mainInteractorModule(MainInteractorModule())
            .filmInteractorModule(FilmInteractorModule())
            .build()

        mainViewModelComponent = DaggerMainViewModelComponent.builder()
            .interactorComponent(interactorComponent)
            .mainViewModelModule(MainViewModelModule(this))
            .build()

        playerViewModelComponent = DaggerPlayerViewModelComponent.builder()
            .interactorComponent(interactorComponent)
            .fetchModule(FetchModule(this))
            .playerViewModelModule(PlayerViewModelModule(this))
            .build()

    }

}