package com.example.simpleplayer.di.modules

import com.example.simpleplayer.di.scopes.InteractorScope
import com.example.simpleplayer.interactor.FilmInteractorImpl
import com.example.simpleplayer.interactor.interfaces.FilmInteractor
import com.example.simpleplayer.repository.AppRepository
import dagger.Module
import dagger.Provides

@InteractorScope
@Module
class FilmInteractorModule {

    @InteractorScope
    @Provides
    fun provideFilmInteractor(repository: AppRepository): FilmInteractor {
        return FilmInteractorImpl(repository)
    }
}