package com.example.simpleplayer.utils.extensions

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun Context.showToast(id: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, id, length).show()
}