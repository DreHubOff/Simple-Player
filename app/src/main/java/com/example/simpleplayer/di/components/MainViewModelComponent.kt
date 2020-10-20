package com.example.simpleplayer.di.components

import android.content.Context
import com.example.simpleplayer.di.modules.MainViewModelModule
import com.example.simpleplayer.di.scopes.MainViewModelScope
import com.example.simpleplayer.application.ui.main.MainFragment
import dagger.Component

@MainViewModelScope
@Component(
    modules = [MainViewModelModule::class],
    dependencies = [InteractorComponent::class]
)
interface MainViewModelComponent {
    val context: Context
    fun inject(mainFragment: MainFragment)
}