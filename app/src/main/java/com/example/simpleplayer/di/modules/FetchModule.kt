package com.example.simpleplayer.di.modules

import android.app.Application
import com.example.simpleplayer.di.scopes.PlayerViewModelScope
import com.tonyodev.fetch2.Fetch
import com.tonyodev.fetch2.FetchConfiguration
import dagger.Module
import dagger.Provides

@Module
class FetchModule(val app: Application) {

    @PlayerViewModelScope
    @Provides
    internal fun provideFetchConfiguration(): FetchConfiguration {
        return FetchConfiguration.Builder(app.applicationContext)
            .setDownloadConcurrentLimit(5)
            .build()
    }

    @PlayerViewModelScope
    @Provides
    internal fun provideFetch(fetchConfig: FetchConfiguration): Fetch {
        return Fetch.getInstance(fetchConfig)
    }
}