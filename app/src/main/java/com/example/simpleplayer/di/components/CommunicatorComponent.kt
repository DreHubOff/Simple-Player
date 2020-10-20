package com.example.simpleplayer.di.components

import android.content.Context
import com.example.simpleplayer.di.modules.CommunicatorModule
import com.example.simpleplayer.di.scopes.CommunicatorScope
import com.example.simpleplayer.repository.network.Communicator
import dagger.Component


@CommunicatorScope
@Component(modules = [CommunicatorModule::class], dependencies = [AppComponent::class])
interface CommunicatorComponent {
    val context: Context
    val communicator: Communicator
}