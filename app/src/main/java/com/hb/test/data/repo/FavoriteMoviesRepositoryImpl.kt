package com.hb.test.data.repo

import com.hb.test.data.dto.MovieDto
import com.hb.test.data.local.dao.FavoriteMovieDao
import com.hb.test.domain.repo.FavoriteMoviesRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteMoviesRepositoryImpl @Inject constructor(
    private val movieDao: FavoriteMovieDao,
) : FavoriteMoviesRepositoryInterface {

    override suspend fun addToFavMovies(movie: MovieDto) {
        movieDao.insert(movie)
    }

    override suspend fun getMovieDetailById(id: Int): Flow<MovieDto?> {
        return movieDao.getMovieDetailById(id)
    }

    override suspend fun getAllFavoriteMovies(): Flow<List<MovieDto>> {
        return try {
            movieDao.getAllMovieDetails()
        } catch (e: Exception) {
            flow { emptyList<MovieDto>() }
        }
    }

    override suspend fun removeMovieById(movieId: Int) {
        movieDao.deleteMovieDetailById(movieId)
    }
}
