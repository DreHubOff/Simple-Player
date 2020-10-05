package com.example.simpleplayer.di.modules

import com.example.simpleplayer.di.scopes.InteractorScope
import com.example.simpleplayer.interactor.MainInteractorImpl
import com.example.simpleplayer.interactor.interfaces.MainInteractor
import com.example.simpleplayer.repository.AppRepository
import dagger.Module
import dagger.Provides

@InteractorScope
@Module
class MainInteractorModule {

    @InteractorScope
    @Provides
    fun provideMainInteractor(repository: AppRepository): MainInteractor {
        return MainInteractorImpl(repository)
    }
}