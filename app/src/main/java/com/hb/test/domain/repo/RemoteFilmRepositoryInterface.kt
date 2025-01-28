package com.hb.test.domain.repo

import androidx.paging.PagingData
import com.hb.test.data.Resource
import com.hb.test.domain.model.Cast
import com.hb.test.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface RemoteFilmRepositoryInterface {

    fun searchFilms(searchParam: String): Flow<PagingData<Movie>>

    fun getTrendingFilms(): Flow<PagingData<Movie>>

    fun getPopularFilms(): Flow<PagingData<Movie>>

    fun getTopRatedMovies(): Flow<PagingData<Movie>>

    fun getUpcomingMovies(): Flow<PagingData<Movie>>

    fun getSimilarMovies(movieId: Int): Flow<PagingData<Movie>>

    suspend fun getMovieDetails(filmId: Int): Flow<Resource<Movie>>

    suspend fun getMovieCast(filmId: Int): Flow<Resource<List<Cast>>>
}
