package com.hb.test.domain.usecases

import com.hb.test.data.repo.RemoteFilmRepositoryImpl
import javax.inject.Inject

class GetMovieCastUseCase @Inject constructor(
    private val remoteFilmRepositoryImpl: RemoteFilmRepositoryImpl
) {

    suspend operator fun invoke(movieId: Int) =
        remoteFilmRepositoryImpl.getMovieCast(filmId = movieId)
}
