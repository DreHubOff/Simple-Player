package com.example.simpleplayer.di.components

import android.content.Context
import com.example.simpleplayer.di.modules.RepositoryModule
import com.example.simpleplayer.di.scopes.RepositoryScope
import com.example.simpleplayer.repository.AppRepository
import dagger.Component

@RepositoryScope
@Component(modules = [RepositoryModule::class], dependencies = [CommunicatorComponent::class])
interface RepositoryComponent {
    val context: Context
    val appRepository: AppRepository
}