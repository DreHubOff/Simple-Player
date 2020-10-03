package com.example.simpleplayer.di.modules

import android.app.Application
import com.example.simpleplayer.di.scopes.PlayerViewModelScope
import com.example.simpleplayer.interactor.interfaces.Interactor
import com.example.simpleplayer.ui.film.PlayerFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.tonyodev.fetch2.Fetch
import dagger.Module
import dagger.Provides


@Module(includes = [FetchModule::class])
class PlayerViewModelModule(val app: Application) {

    @PlayerViewModelScope
    @Provides
    internal fun providePlayer(): ExoPlayer {
        return SimpleExoPlayer.Builder(app.applicationContext).build()
    }

    @PlayerViewModelScope
    @Provides
    internal fun providePlayerFactory(interactor: Interactor, player: ExoPlayer, fetch: Fetch) =
        PlayerFactory(app, interactor, player, fetch)
}