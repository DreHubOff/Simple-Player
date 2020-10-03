package com.example.simpleplayer.di.modules

import com.example.simpleplayer.di.scopes.RepositoryScope
import com.example.simpleplayer.repository.AppRepository
import com.example.simpleplayer.repository.AppRepositoryImpl
import com.example.simpleplayer.repository.db.FilmDataBase
import com.example.simpleplayer.repository.network.Communicator
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule(private val fillDataBase: FilmDataBase) {
    @RepositoryScope
    @Provides
    internal fun provideRepository(communicator: Communicator): AppRepository {
        return AppRepositoryImpl(database = fillDataBase, communicator = communicator)
    }
}