package com.example.simpleplayer.utils.actions

import android.content.Intent
import com.google.android.exoplayer2.ExoPlayer

sealed class PlayerViewAction {
    class Error(val errorMsg: String) : PlayerViewAction()
    class StartPlayer(val player: ExoPlayer) : PlayerViewAction()
    class ViewingTextChange(val text: String) : PlayerViewAction()
    class ChangeConfig(val orientationFlag: Int) : PlayerViewAction()
    class RequestPermission(val permissions: Array<String>, val requestCode: Int) : PlayerViewAction()
    object DownloadingStarted : PlayerViewAction()
}
