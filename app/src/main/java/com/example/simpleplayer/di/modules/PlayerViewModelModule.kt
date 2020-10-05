package com.example.simpleplayer.di.modules

import android.app.Application
import com.example.simpleplayer.di.scopes.PlayerViewModelScope
import com.example.simpleplayer.interactor.interfaces.FilmInteractor
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
        return SimpleExoPlayer.Builder(app.applicationContext).build().apply {
            setForegroundMode(false)
        }
    }

    @PlayerViewModelScope
    @Provides
    internal fun providePlayerFactory(
        filmInteractor: FilmInteractor,
        player: ExoPlayer,
        fetch: Fetch
    ) =
        PlayerFactory(app, filmInteractor, player, fetch)
}