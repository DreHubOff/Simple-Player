package com.example.simpleplayer.di.components

import com.example.simpleplayer.di.modules.MainViewModelModule
import com.example.simpleplayer.di.scopes.MainViewModelScope
import com.example.simpleplayer.ui.main.MainFragment
import dagger.Component

@MainViewModelScope
@Component(modules = [MainViewModelModule::class], dependencies = [InteractorComponent::class])
interface MainViewModelComponent {
    fun inject(mainFragment: MainFragment)
}