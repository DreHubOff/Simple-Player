package com.example.simpleplayer.di.module

import android.app.Application
import com.example.simpleplayer.di.scope.MainViewModelScope
import com.example.simpleplayer.interactor.MainInteractorImpl
import com.example.simpleplayer.interactor.interfaces.MainInteractor
import com.example.simpleplayer.repository.AppRepository
import com.example.simpleplayer.ui.main.MainFactory
import dagger.Module
import dagger.Provides


@Module
class MainViewModelModule(val app: Application) {

    @MainViewModelScope
    @Provides
    internal fun provideInteractor(repository: AppRepository) = MainInteractorImpl(repository)

    @MainViewModelScope
    @Provides
    internal fun provideMainFactory(interactor: MainInteractor)
    = MainFactory(app, interactor)

}