package com.hb.test.domain.usecases

import com.hb.test.domain.mappers.mapToMovie
import com.hb.test.domain.model.Movie
import com.hb.test.domain.repo.FavoriteMoviesRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val favoriteMoviesRepositoryInterface: FavoriteMoviesRepositoryInterface
) {

    suspend operator fun invoke(): Flow<List<Movie>> {
        return favoriteMoviesRepositoryInterface.getAllFavoriteMovies().map { listMovies ->
            listMovies.map { movieDto -> movieDto.mapToMovie() }
        }
    }
}
