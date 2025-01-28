package com.hb.test.domain.repo

import com.hb.test.data.dto.MovieDto
import kotlinx.coroutines.flow.Flow

interface FavoriteMoviesRepositoryInterface {
    suspend fun addToFavMovies(movie: MovieDto)
    suspend fun getMovieDetailById(id: Int): Flow<MovieDto?>
    suspend fun getAllFavoriteMovies(): Flow<List<MovieDto>>
    suspend fun removeMovieById(movieId: Int)
}
