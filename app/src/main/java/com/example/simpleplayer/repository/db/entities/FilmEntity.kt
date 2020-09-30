package com.example.simpleplayer.repository.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey(autoGenerate = true)
    val _ID: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "background_image_url")
    val backgroundImageURL: String,
    @ColumnInfo(name = "film_url")
    val filmURL: String,
    @ColumnInfo(name = "offline_viewing")
    val offlineViewing: Boolean = false,
    @ColumnInfo(name = "film_file_link")
    val filmFileLink: String? = null
    )