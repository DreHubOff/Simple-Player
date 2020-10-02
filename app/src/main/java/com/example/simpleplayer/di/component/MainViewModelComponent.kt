package com.example.simpleplayer.di.component

import android.app.Application
import com.example.simpleplayer.di.module.MainViewModelModule
import com.example.simpleplayer.di.scope.MainViewModelScope
import com.example.simpleplayer.ui.main.MainFragment
import dagger.Component

@MainViewModelScope
@Component(modules = [MainViewModelModule::class], dependencies = [InteractorComponent::class])
interface MainViewModelComponent {
    fun inject(mainFragment: MainFragment)
}