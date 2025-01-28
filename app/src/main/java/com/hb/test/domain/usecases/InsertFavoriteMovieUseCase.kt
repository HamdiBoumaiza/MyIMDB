package com.hb.test.domain.usecases

import com.hb.test.domain.mappers.mapToMovieDto
import com.hb.test.domain.model.Movie
import com.hb.test.domain.repo.FavoriteMoviesRepositoryInterface
import javax.inject.Inject

class InsertFavoriteMovieUseCase @Inject constructor(
    private val favoriteMoviesRepositoryInterface: FavoriteMoviesRepositoryInterface
) {

    suspend operator fun invoke(movie: Movie) =
        favoriteMoviesRepositoryInterface.addToFavMovies(movie.mapToMovieDto())
}
