package com.example.simpleplayer.di.component

import com.example.simpleplayer.di.module.CommunicatorModule
import com.example.simpleplayer.di.scope.CommunicatorScope
import com.example.simpleplayer.repository.network.Communicator
import dagger.Component


@CommunicatorScope
@Component(modules = [CommunicatorModule::class])
interface CommunicatorComponent {
    val communicator: Communicator
}