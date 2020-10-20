package com.example.simpleplayer.di.components

import com.example.simpleplayer.application.services.DownloadService
import com.example.simpleplayer.di.modules.FetchModule
import com.example.simpleplayer.di.scopes.DownloadingServiceScope
import dagger.Component

@DownloadingServiceScope
@Component(modules = [FetchModule::class], dependencies = [AppComponent::class])
interface DownloadingServiceComponent {
    fun inject(downloadingService: DownloadService)
}