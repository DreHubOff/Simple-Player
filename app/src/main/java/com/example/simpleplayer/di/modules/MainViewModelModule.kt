package com.example.simpleplayer.di.modules

import android.content.Context
import com.example.simpleplayer.App
import com.example.simpleplayer.di.scopes.MainViewModelScope
import com.example.simpleplayer.interactor.interfaces.MainInteractor
import com.example.simpleplayer.application.ui.main.MainFactory
import dagger.Module
import dagger.Provides


@Module
class MainViewModelModule {

    @MainViewModelScope
    @Provides
    internal fun provideMainFactory(mainInteractor: MainInteractor, context: Context) =
        MainFactory(context as App, mainInteractor)

}