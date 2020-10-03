package com.example.simpleplayer.di.modules

import android.app.Application
import com.example.simpleplayer.di.scopes.MainViewModelScope
import com.example.simpleplayer.interactor.interfaces.Interactor
import com.example.simpleplayer.ui.main.MainFactory
import dagger.Module
import dagger.Provides


@Module
class MainViewModelModule(val app: Application) {

    @MainViewModelScope
    @Provides
    internal fun provideMainFactory(interactor: Interactor)
    = MainFactory(app, interactor)

}