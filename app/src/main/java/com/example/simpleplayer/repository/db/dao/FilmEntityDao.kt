package com.example.simpleplayer.repository.db.dao

import androidx.room.*
import com.example.simpleplayer.repository.db.entities.FilmEntity
import io.reactivex.Single

@Dao
interface FilmEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(filmsList: List<FilmEntity>)

    @Query("SELECT * FROM films")
    fun selectAll(): Single<List<FilmEntity>>

    @Query("SELECT * FROM films WHERE _ID =:id")
    fun getFilmById(id: Int): Single<FilmEntity>

    @Query("UPDATE films SET offline_viewing =:offlineViewing, film_file_link=:filmFileLink WHERE _ID =:id")
    fun updateSingle(offlineViewing: Boolean, filmFileLink: String, id: Int)
}