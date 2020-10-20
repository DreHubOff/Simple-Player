package com.example.simpleplayer.di.modules

import android.content.Context
import com.example.simpleplayer.App
import com.example.simpleplayer.di.scopes.PlayerViewModelScope
import com.example.simpleplayer.interactor.interfaces.FilmInteractor
import com.example.simpleplayer.ui.film.PlayerFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.Module
import dagger.Provides


@Module
class PlayerViewModelModule {

    @Provides
    internal fun providePlayer(context: Context): ExoPlayer {
        return SimpleExoPlayer.Builder(context).build().apply {
            setForegroundMode(false)
        }
    }

    @PlayerViewModelScope
    @Provides
    internal fun providePlayerFactory(
        filmInteractor: FilmInteractor,
        context: Context
    ) =
        PlayerFactory(context as App, filmInteractor)
}