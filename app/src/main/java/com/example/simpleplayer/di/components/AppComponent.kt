package com.example.simpleplayer.di.components

import android.content.Context
import com.example.simpleplayer.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    val context: Context
}