package com.example.simpleplayer.utils.actions

import com.example.simpleplayer.model.Film


sealed class MainViewAction {
    class ERROR(val errorMsg: String) : MainViewAction()
    class ITEM_CLICK(val film: Film) : MainViewAction()
}