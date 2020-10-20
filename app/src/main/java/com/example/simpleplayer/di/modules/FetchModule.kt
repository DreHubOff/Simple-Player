package com.example.simpleplayer.di.modules

import android.content.Context
import com.example.simpleplayer.di.scopes.DownloadingServiceScope
import com.tonyodev.fetch2.Fetch
import com.tonyodev.fetch2.FetchConfiguration
import dagger.Module
import dagger.Provides

@Module
class FetchModule {

    @DownloadingServiceScope
    @Provides
    internal fun provideFetchConfiguration(context: Context): FetchConfiguration {
        return FetchConfiguration.Builder(context)
            .setDownloadConcurrentLimit(5)
            .build()
    }

    @DownloadingServiceScope
    @Provides
    internal fun provideFetch(fetchConfig: FetchConfiguration): Fetch {
        return Fetch.getInstance(fetchConfig)
    }
}