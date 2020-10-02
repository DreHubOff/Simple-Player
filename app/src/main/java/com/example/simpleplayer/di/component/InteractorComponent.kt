package com.example.simpleplayer.di.component

import com.example.simpleplayer.di.module.InteractorModule
import com.example.simpleplayer.di.scope.InteractorScope
import dagger.Component

@InteractorScope
@Component(modules = [InteractorModule::class], dependencies = [RepositoryComponent::class])
interface InteractorComponent {
}