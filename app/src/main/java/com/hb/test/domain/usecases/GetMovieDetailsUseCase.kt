package com.hb.test.domain.usecases

import com.hb.test.domain.repo.RemoteFilmRepositoryInterface
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val remoteFilmRepositoryImpl: RemoteFilmRepositoryInterface
) {

    suspend operator fun invoke(movieId: Int) =
        remoteFilmRepositoryImpl.getMovieDetails(filmId = movieId)
}
