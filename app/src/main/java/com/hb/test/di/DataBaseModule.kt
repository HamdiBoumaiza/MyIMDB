package com.hb.test.di

import android.content.Context
import androidx.room.Room
import com.hb.test.data.local.FavoriteMoviesDatabase
import com.hb.test.data.local.dao.FavoriteMovieDao
import com.hb.test.data.repo.FavoriteMoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): FavoriteMoviesDatabase {
        return Room.databaseBuilder(
            context,
            FavoriteMoviesDatabase::class.java,
            "movies.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDetailDao(moviesDatabase: FavoriteMoviesDatabase): FavoriteMovieDao =
        moviesDatabase.getFavoriteMovieDetailDao()

    @Singleton
    @Provides
    fun provideLocalMovieRepo(movieDao: FavoriteMovieDao): FavoriteMoviesRepositoryImpl =
        FavoriteMoviesRepositoryImpl(movieDao)
}
