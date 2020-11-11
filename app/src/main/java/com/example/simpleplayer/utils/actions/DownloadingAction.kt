package com.example.simpleplayer.utils.actions

sealed class DownloadingAction {
    class ERROR(val msg: String) : DownloadingAction()
}