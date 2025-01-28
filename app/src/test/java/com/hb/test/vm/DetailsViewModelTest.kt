package com.hb.test.vm

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.hb.test.R
import com.hb.test.data.Resource
import com.hb.test.domain.model.Cast
import com.hb.test.domain.model.Movie
import com.hb.test.domain.usecases.DeleteFavoriteMovieUseCase
import com.hb.test.domain.usecases.GetFavoriteMovieByIdUseCase
import com.hb.test.domain.usecases.GetMovieCastUseCase
import com.hb.test.domain.usecases.GetMovieDetailsUseCase
import com.hb.test.domain.usecases.GetSimilarMoviesUseCase
import com.hb.test.domain.usecases.InsertFavoriteMovieUseCase
import com.hb.test.features.details.DetailsScreenCastUIState
import com.hb.test.features.details.DetailsScreenUIState
import com.hb.test.features.details.DetailsViewModel
import com.hb.test.utils.network.NoNetworkException
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    @RelaxedMockK
    lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @RelaxedMockK
    lateinit var getMovieCastUseCase: GetMovieCastUseCase

    @RelaxedMockK
    lateinit var getSimilarMoviesUseCase: GetSimilarMoviesUseCase

    @RelaxedMockK
    lateinit var insertFavoriteMovieUseCase: InsertFavoriteMovieUseCase

    @RelaxedMockK
    lateinit var deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase

    @RelaxedMockK
    lateinit var getFavoriteMovieByIdUseCase: GetFavoriteMovieByIdUseCase

    @InjectMockKs
    lateinit var detailsViewModel: DetailsViewModel

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
    fun `Get movie details with success`() = runTest {
        // Given
        val expectedResult = Movie()
        coEvery { getMovieDetailsUseCase.invoke(12) } coAnswers {
            flow { emit(Resource.Success(data = expectedResult)) }
        }
        // when
        detailsViewModel.getMovieDetails(12)
        // Then
        detailsViewModel.movieDetails.test {
            awaitItem()
            advanceUntilIdle()
            val state = awaitItem()
            advanceUntilIdle()
            assertEquals(state, DetailsScreenUIState.Success(expectedResult))
            cancel()
        }

        coVerify { getMovieDetailsUseCase.invoke(12) }
    }

    @Test
    fun `Get movie details with failure`() = runTest {
        // Given
        coEvery { getMovieDetailsUseCase.invoke(12) } coAnswers {
            flow { emit(Resource.Error(NoNetworkException("Error"))) }
        }
        // when
        detailsViewModel.getMovieDetails(12)
        // Then
        detailsViewModel.movieDetails.test {
            awaitItem()
            advanceUntilIdle()
            val state = awaitItem()
            advanceUntilIdle()
            assertEquals(state, DetailsScreenUIState.Error(R.string.no_network_exception))
        }
        coVerify { getMovieDetailsUseCase.invoke(12) }
    }

    @Test
    fun `Get movie cast with success`() = runTest {
        // Given
        val expectedResult = listOf(Cast())
        coEvery { getMovieCastUseCase.invoke(12) } coAnswers {
            flow { emit(Resource.Success(data = expectedResult)) }
        }
        // when
        detailsViewModel.getMovieCast(12)
        // Then
        detailsViewModel.movieCast.test {
            awaitItem()
            advanceUntilIdle()
            val state = awaitItem()
            advanceUntilIdle()
            assertEquals(state, DetailsScreenCastUIState.Success(expectedResult))
        }
        coVerify { getMovieCastUseCase.invoke(12) }
    }

    @Test
    fun `Get movie cast with failure`() = runTest {
        // Given
        coEvery { getMovieCastUseCase.invoke(12) } coAnswers {
            flow { emit(Resource.Error(NoNetworkException("Error"))) }
        }
        // when
        detailsViewModel.getMovieCast(12)
        // Then
        detailsViewModel.movieCast.test {
            awaitItem()
            advanceUntilIdle()
            val state = awaitItem()
            advanceUntilIdle()
            assertEquals(state, DetailsScreenCastUIState.Error(R.string.no_network_exception))
        }
        coVerify { getMovieCastUseCase.invoke(12) }
    }

    @Test
    fun `Get similar movies with success`() = runTest {
        // Given
        val similarMoviesList = listOf(Movie())
        val pagingData = PagingData.from(similarMoviesList)
        coEvery { getSimilarMoviesUseCase.invoke(12) } coAnswers { flow { emit(pagingData) } }
        // when
        detailsViewModel.getSimilarMovies(12)
        // Then
        detailsViewModel.similarMovies.test {
            awaitItem()
            advanceUntilIdle()
            val items = awaitItem()
            val list = flowOf(items).asSnapshot()
            assertEquals(similarMoviesList[0].title, list[0].title)
        }
        coVerify { getSimilarMoviesUseCase.invoke(12) }
    }

    @Test
    fun `check a favorite movie with success`() = runTest {
        // Given
        coEvery { getFavoriteMovieByIdUseCase(0) } coAnswers { flow { emit(null) } }
        // when
        detailsViewModel.checkFavMovies(0)
        // Then
        detailsViewModel.isFavoriteMovie.test {
            awaitItem()
            val list = awaitItem()
            assert(!list)
        }
        coVerify { getFavoriteMovieByIdUseCase(0) }
    }
}
