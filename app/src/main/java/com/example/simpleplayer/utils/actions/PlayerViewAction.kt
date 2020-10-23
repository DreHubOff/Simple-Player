package com.example.simpleplayer.utils.actions

import android.content.Intent
import com.google.android.exoplayer2.ExoPlayer

sealed class PlayerViewAction {
    class ERROR(val errorMsg: String) : PlayerViewAction()
    class START_PLAYER(val player: ExoPlayer) : PlayerViewAction()
    class VIEWING_TEXT_CHANGE(val text: String) : PlayerViewAction()
    class CHANGE_CONFIG(val orientationFlag: Int) : PlayerViewAction()
    class START_SERVICE(val intent: Intent) : PlayerViewAction()
}
