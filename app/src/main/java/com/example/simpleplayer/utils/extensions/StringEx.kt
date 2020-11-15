package com.example.simpleplayer.utils.extensions

fun String.getFileName() =
    this.split("/").last()
