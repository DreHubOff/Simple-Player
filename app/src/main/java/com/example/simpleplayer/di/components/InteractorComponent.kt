package com.example.simpleplayer.di.components

import com.example.simpleplayer.di.modules.InteractorModule
import com.example.simpleplayer.di.scopes.InteractorScope
import com.example.simpleplayer.interactor.interfaces.Interactor
import dagger.Component

@InteractorScope
@Component(modules = [InteractorModule::class], dependencies = [RepositoryComponent::class])
interface InteractorComponent {
    val interactor: Interactor
}