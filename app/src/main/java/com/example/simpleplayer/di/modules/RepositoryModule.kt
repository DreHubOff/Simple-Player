package com.example.simpleplayer.di.modules

import android.content.Context
import com.example.simpleplayer.di.scopes.RepositoryScope
import com.example.simpleplayer.repository.AppRepository
import com.example.simpleplayer.repository.AppRepositoryImpl
import com.example.simpleplayer.repository.db.FilmDataBase
import com.example.simpleplayer.repository.network.Communicator
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule() {
    @RepositoryScope
    @Provides
    internal fun provideRepository(
        communicator: Communicator,
        fillDataBase: FilmDataBase
    ): AppRepository {
        return AppRepositoryImpl(database = fillDataBase, communicator = communicator)
    }

    @RepositoryScope
    @Provides
    internal fun provideDataBase(context: Context): FilmDataBase {
        return FilmDataBase.getInstance(context)
    }
}