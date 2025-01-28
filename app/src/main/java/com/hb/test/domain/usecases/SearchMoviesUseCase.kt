package com.hb.test.domain.usecases

import com.hb.test.domain.repo.RemoteFilmRepositoryInterface
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val remoteFilmRepositoryImpl: RemoteFilmRepositoryInterface
) {

    operator fun invoke(searchParam: String) =
        remoteFilmRepositoryImpl.searchFilms(searchParam)
}
