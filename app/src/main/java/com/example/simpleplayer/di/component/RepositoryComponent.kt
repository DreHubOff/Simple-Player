package com.example.simpleplayer.di.component

import com.example.simpleplayer.di.module.RepositoryModule
import com.example.simpleplayer.di.scope.RepositoryScope
import com.example.simpleplayer.repository.db.FilmDataBase
import dagger.Component

@RepositoryScope
@Component(modules = [RepositoryModule::class], dependencies = [CommunicatorComponent::class])
interface RepositoryComponent {
    val filmDataBase: FilmDataBase
}