package com.example.simpleplayer.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simpleplayer.repository.db.dao.FilmEntityDao
import com.example.simpleplayer.repository.db.entities.FilmEntity

@Database(entities = [FilmEntity::class], version = 1, exportSchema = false)
abstract class FilmDataBase : RoomDatabase() {
    abstract fun getFilmDao(): FilmEntityDao

    companion object {
        fun getInstance(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            FilmDataBase::class.java,
            "films_db"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}