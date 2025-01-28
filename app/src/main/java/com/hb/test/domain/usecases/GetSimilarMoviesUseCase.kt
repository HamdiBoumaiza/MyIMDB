package com.hb.test.domain.usecases

import com.hb.test.data.repo.RemoteFilmRepositoryImpl
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(
    private val remoteFilmRepositoryImpl: RemoteFilmRepositoryImpl
) {
    operator fun invoke(movieId: Int) =
        remoteFilmRepositoryImpl.getSimilarMovies(movieId = movieId)
}
