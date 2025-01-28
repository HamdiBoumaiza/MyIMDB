package com.hb.test.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hb.test.data.dto.MovieDto
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieDto)

    @Query("DELETE FROM movie WHERE id = :id")
    suspend fun deleteMovieDetailById(id: Int)

    @Query("Select * From movie Where id = :id")
    fun getMovieDetailById(id: Int): Flow<MovieDto?>

    @Query("SELECT * FROM movie")
    fun getAllMovieDetails(): Flow<List<MovieDto>>
}
