package com.example.simpleplayer.repository.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class FilmEntity @JvmOverloads constructor(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "rating")
    val rating: Double,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String,
    @ColumnInfo(name = "film_url")
    val filmUrl: String,

    @PrimaryKey(autoGenerate = true)
    val _ID: Int = 0,
    @ColumnInfo(name = "offline_viewing")
    val offlineViewing: Boolean = false,
    @ColumnInfo(name = "film_file_link")
    val filmFileLink: String? = null
    )