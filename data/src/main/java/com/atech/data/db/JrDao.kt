package com.atech.data.db

import androidx.room.*

/**
 * Created by Abraham Lay on 29/04/20.
 */

@Dao
interface JrDao {
//
//    @Query("SELECT * FROM MovieModel")
//    fun selectFavoriteMovie(): Flowable<List<MovieModel>?>
//
//    @Query("SELECT * FROM MovieModel WHERE id = :id")
//    fun select(id: Int): Flowable<MovieModel?>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(movieModel: MovieModel): Long
//
//    @Delete
//    fun delete(t: MovieModel): Int
//
    @Query("DELETE FROM MovieModel")
    fun deleteAll()

}