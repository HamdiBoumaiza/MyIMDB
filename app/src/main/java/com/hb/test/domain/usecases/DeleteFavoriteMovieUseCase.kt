package com.hb.test.domain.usecases

import com.hb.test.domain.repo.FavoriteMoviesRepositoryInterface
import javax.inject.Inject

class DeleteFavoriteMovieUseCase @Inject constructor(
    private val favoriteMoviesRepositoryInterface: FavoriteMoviesRepositoryInterface
) {

    suspend operator fun invoke(id: Int) =
        favoriteMoviesRepositoryInterface.removeMovieById(id)
}
