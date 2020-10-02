package com.example.simpleplayer.di.module

import com.example.simpleplayer.di.scope.CommunicatorScope
import com.example.simpleplayer.repository.network.Communicator
import com.example.simpleplayer.repository.network.CommunicatorImpl
import com.example.simpleplayer.repository.network.service.DemoFilmApi
import com.example.simpleplayer.repository.network.service.FilmApiService
import dagger.Module
import dagger.Provides


@Module
class CommunicatorModule {
    @CommunicatorScope
    @Provides
    fun provideCommunicator(
        demoFilmApi: DemoFilmApi,
        filmApiService: FilmApiService
    ): Communicator {
        return CommunicatorImpl(
            demoFilmApi = demoFilmApi,
            filmApiService = filmApiService
        )
    }

    @CommunicatorScope
    @Provides
    fun provideDemoFilmApi() = DemoFilmApi()

    @CommunicatorScope
    @Provides
    fun provideFilmApiService() = FilmApiService()
}