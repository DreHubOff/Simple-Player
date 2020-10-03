package com.example.simpleplayer.utils

import android.widget.Toast
import com.example.simpleplayer.ui.film.PlayerFragment

fun PlayerFragment.showToast(text: String){
    activity?.run {Toast.makeText(this, text, Toast.LENGTH_SHORT).show() }
}