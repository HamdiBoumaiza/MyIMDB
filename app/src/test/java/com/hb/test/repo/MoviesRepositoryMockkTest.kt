package com.hb.test.repo

import com.hb.test.data.RemoteApi
import com.hb.test.data.Resource
import com.hb.test.data.dto.MovieDto
import com.hb.test.data.repo.RemoteFilmRepositoryImpl
import com.hb.test.domain.model.Genre
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MoviesRepositoryMockkTest {

    // private val apiService = mockk<RemoteApi>(relaxed = true)

    @RelaxedMockK
    lateinit var apiService: RemoteApi

    private lateinit var newsApiRepositoryImpl: RemoteFilmRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        // apiService = mockk()
        newsApiRepositoryImpl = RemoteFilmRepositoryImpl(apiService)
    }

    @Test
    fun `Get movie data with success `() = runTest {
        coEvery { apiService.getMovieDetails(1) } returns mockedMovie
        val result = newsApiRepositoryImpl.getMovieDetails(1)

        result.collect {
            if (it is Resource.Success) {
                assert(it.data.id == 1)
                assert(it.data.genres == emptyList<Genre>())
            }
        }
    }

    companion object {
        val mockedMovie = MovieDto(
            backdropPath = "",
            posterPath = "",
            genreDtos = emptyList(),
            id = 1,
            overview = "",
            releaseDate = "",
            title = "",
            voteAverage = 1.1,
            voteCount = 1
        )
    }
}
