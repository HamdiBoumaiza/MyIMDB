package com.hb.test.di

import com.hb.test.data.RemoteApi
import com.hb.test.data.local.dao.FavoriteMovieDao
import com.hb.test.data.repo.FavoriteMoviesRepositoryImpl
import com.hb.test.data.repo.RemoteFilmRepositoryImpl
import com.hb.test.domain.repo.FavoriteMoviesRepositoryInterface
import com.hb.test.domain.repo.RemoteFilmRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteMoviesRepository(
        api: RemoteApi
    ): RemoteFilmRepositoryInterface = RemoteFilmRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideFavoriteMoviesRepository(
        dao: FavoriteMovieDao
    ): FavoriteMoviesRepositoryInterface = FavoriteMoviesRepositoryImpl(dao)
}
