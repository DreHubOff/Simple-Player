package com.example.simpleplayer.utils.extensions

import android.app.Application
import androidx.lifecycle.AndroidViewModel

fun AndroidViewModel.getString(id: Int): String {
    return getApplication<Application>().getString(id)
}