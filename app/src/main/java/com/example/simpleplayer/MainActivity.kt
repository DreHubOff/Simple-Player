package com.example.simpleplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simpleplayer.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    interface BeckPressedHelper{
        fun backPressed()
    }

    var backPressedHelper: BeckPressedHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.getInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        backPressedHelper?.backPressed()
    }
}