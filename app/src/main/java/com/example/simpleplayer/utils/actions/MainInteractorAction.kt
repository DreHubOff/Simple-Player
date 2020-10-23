package com.example.simpleplayer.utils.actions

import com.example.simpleplayer.model.Film

sealed class MainInteractorAction {
    class SUCCESS(val filmList: List<Film>) : MainInteractorAction()
    class ERROR(val errorMsgId: Int) : MainInteractorAction()
}

