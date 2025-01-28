package com.hb.test.vm

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.hb.test.data.repo.RemoteFilmRepositoryImpl
import com.hb.test.domain.model.Movie
import com.hb.test.domain.usecases.SearchMoviesUseCase
import com.hb.test.prensentation.features.home.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @RelaxedMockK
    lateinit var remoteFilmRepositoryImpl: RemoteFilmRepositoryImpl

    @RelaxedMockK
    lateinit var searchMoviesUseCase: SearchMoviesUseCase

    @InjectMockKs
    lateinit var homeViewModel: HomeViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `Get popular movies with success`() = runTest {
        // Given
        val popularMoviesList = listOf(Movie())
        val pagingData = PagingData.from(popularMoviesList)
        coEvery { remoteFilmRepositoryImpl.getPopularFilms() } coAnswers { flow { emit(pagingData) } }
        // when
        homeViewModel.getPopularMovies()
        // Then
        homeViewModel.popularMoviesState.test {
            awaitItem()
            val list = flowOf(awaitItem()).asSnapshot()
            assertEquals(popularMoviesList[0].title, list[0].title)
        }
    }

    @Test
    fun `Get trending movies with success`() = runTest {
        // Given
        val popularMoviesList = listOf(Movie())
        val pagingData = PagingData.from(popularMoviesList)
        coEvery { remoteFilmRepositoryImpl.getTrendingFilms() } coAnswers { flow { emit(pagingData) } }
        // when
        homeViewModel.getTrendingMovies()
        // Then
        homeViewModel.trendingMoviesState.test {
            awaitItem()
            val list = flowOf(awaitItem()).asSnapshot()
            assertEquals(popularMoviesList[0].title, list[0].title)
        }
    }
}
