package com.example.simpleplayer.di.components

import com.example.simpleplayer.di.modules.CommunicatorModule
import com.example.simpleplayer.di.scopes.CommunicatorScope
import com.example.simpleplayer.repository.network.Communicator
import dagger.Component


@CommunicatorScope
@Component(modules = [CommunicatorModule::class])
interface CommunicatorComponent {
    val communicator: Communicator
}