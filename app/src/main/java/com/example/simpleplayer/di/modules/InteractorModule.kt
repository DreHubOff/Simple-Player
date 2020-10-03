package com.example.simpleplayer.di.modules

import com.example.simpleplayer.di.scopes.InteractorScope
import com.example.simpleplayer.interactor.InteractorImpl
import com.example.simpleplayer.interactor.interfaces.Interactor
import com.example.simpleplayer.repository.AppRepository
import dagger.Module
import dagger.Provides

@InteractorScope
@Module
class InteractorModule {

    @InteractorScope
    @Provides
    fun provideInteractor(repository: AppRepository): Interactor {
        return InteractorImpl(repository)
    }
}