package com.example.simpleplayer.di.components

import com.example.simpleplayer.di.modules.PlayerViewModelModule
import com.example.simpleplayer.di.scopes.PlayerViewModelScope
import com.example.simpleplayer.application.ui.film.PlayerActivity
import com.google.android.exoplayer2.ExoPlayer
import dagger.Component

@PlayerViewModelScope
@Component(
    modules = [PlayerViewModelModule::class],
    dependencies = [InteractorComponent::class]
)
interface PlayerViewModelComponent {
    fun inject(playerActivity: PlayerActivity)
    fun getPlayer(): ExoPlayer
}