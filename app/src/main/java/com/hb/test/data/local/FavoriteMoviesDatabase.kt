package com.hb.test.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hb.test.data.dto.MovieDto
import com.hb.test.data.local.dao.FavoriteMovieDao
import com.hb.test.data.local.typeconverter.MovieTypeConverter

@TypeConverters(MovieTypeConverter::class)
@Database(version = 2, entities = [MovieDto::class], exportSchema = false)
abstract class FavoriteMoviesDatabase : RoomDatabase() {
    abstract fun getFavoriteMovieDetailDao(): FavoriteMovieDao
}
