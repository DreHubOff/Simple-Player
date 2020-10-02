package com.example.simpleplayer.di.component

import com.example.simpleplayer.di.module.InteractorModule
import com.example.simpleplayer.di.scope.InteractorScope
import com.example.simpleplayer.interactor.interfaces.MainInteractor
import dagger.Component

@InteractorScope
@Component(modules = [InteractorModule::class], dependencies = [RepositoryComponent::class])
interface InteractorComponent {
    val interactor: MainInteractor
}