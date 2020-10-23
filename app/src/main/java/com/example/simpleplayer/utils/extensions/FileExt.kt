package com.example.simpleplayer.utils.extensions

import java.io.File

fun File.getFileSizeMegaBytes() = this.length() / 1024 * 1024

fun File.getOrNew(): File {
    if (this.exists() || this.getFileSizeMegaBytes() < 2) {
        this.delete()
        this.createNewFile()
        this.mkdirs()
    }
    return this
}