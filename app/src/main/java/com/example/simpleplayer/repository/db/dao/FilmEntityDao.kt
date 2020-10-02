package com.example.simpleplayer.repository.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simpleplayer.repository.db.entities.FilmEntity
import io.reactivex.Single

interface FilmEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(filmsList: List<FilmEntity>)

    @Query("SELECT * FROM films")
    fun selectAll(): Single<List<FilmEntity>>

    @Query("SELECT * FROM films WHERE _ID =:id")
    fun getFilmById(id: Int): Single<FilmEntity>
}