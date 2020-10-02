package com.example.simpleplayer.di.module

import com.example.simpleplayer.di.scope.InteractorScope
import com.example.simpleplayer.interactor.MainInteractorImpl
import com.example.simpleplayer.interactor.interfaces.MainInteractor
import com.example.simpleplayer.repository.AppRepository
import dagger.Module
import dagger.Provides

@InteractorScope
@Module
class InteractorModule {

    @InteractorScope
    @Provides
    fun provideInteractor(repository: AppRepository): MainInteractor {
        return MainInteractorImpl(repository)
    }
}