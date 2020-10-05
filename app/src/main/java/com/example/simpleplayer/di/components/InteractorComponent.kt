package com.example.simpleplayer.di.components

import com.example.simpleplayer.di.modules.FilmInteractorModule
import com.example.simpleplayer.di.modules.MainInteractorModule
import com.example.simpleplayer.di.scopes.InteractorScope
import com.example.simpleplayer.interactor.interfaces.FilmInteractor
import com.example.simpleplayer.interactor.interfaces.MainInteractor
import dagger.Component

@InteractorScope
@Component(modules = [MainInteractorModule::class, FilmInteractorModule::class], dependencies = [RepositoryComponent::class])
interface InteractorComponent {
    val mainInteractor: MainInteractor
    val filmInteractor: FilmInteractor
}