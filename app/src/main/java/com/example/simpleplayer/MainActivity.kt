package com.example.simpleplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.view.WindowManager
import com.example.simpleplayer.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    interface BeckPressedHelper {
        fun backPressed()
    }

    var mainFragmentExit = false

    var backPressedHelper: BeckPressedHelper? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }else{
           //
        }
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.getInstance())
                .commitNow()
            window.setFlags(
                WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW,
                WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW
            )
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(mainFragmentExit.javaClass.name, mainFragmentExit)
    }

    override fun onBackPressed() {
        backPressedHelper?.backPressed()
    }
}