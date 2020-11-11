package com.example.simpleplayer

import android.app.Application
import android.content.Intent
import com.example.simpleplayer.di.components.*
import com.example.simpleplayer.di.modules.*
import com.example.simpleplayer.repository.db.FilmDataBase


class App : Application() {

    lateinit var mainViewModelComponent: MainViewModelComponent
        private set
    lateinit var playerViewModelComponent: PlayerViewModelComponent
        private set
    lateinit var downloadingServiceComponent: DownloadingServiceComponent
        private set

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }


    private fun initDagger() {
        val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        val communicatorComponent = DaggerCommunicatorComponent.builder()
            .appComponent(appComponent)
            .communicatorModule(CommunicatorModule())
            .build()

        val repositoryComponent = DaggerRepositoryComponent.builder()
            .communicatorComponent(communicatorComponent)
            .repositoryModule(RepositoryModule())
            .build()

        val interactorComponent = DaggerInteractorComponent.builder()
            .repositoryComponent(repositoryComponent)
            .mainInteractorModule(MainInteractorModule())
            .filmInteractorModule(FilmInteractorModule())
            .build()

        mainViewModelComponent = DaggerMainViewModelComponent.builder()
            .interactorComponent(interactorComponent)
            .mainViewModelModule(MainViewModelModule())
            .build()

        playerViewModelComponent = DaggerPlayerViewModelComponent.builder()
            .interactorComponent(interactorComponent)
            .playerViewModelModule(PlayerViewModelModule())
            .build()

        downloadingServiceComponent = DaggerDownloadingServiceComponent.builder()
            .appComponent(appComponent)
            .fetchModule(FetchModule())
            .downloadingNotificationModule(DownloadingNotificationModule())
            .build()

    }

}