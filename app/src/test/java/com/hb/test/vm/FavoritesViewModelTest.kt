package com.hb.test.vm

import app.cash.turbine.test
import com.hb.test.domain.model.Movie
import com.hb.test.domain.usecases.GetFavoriteMoviesUseCase
import com.hb.test.prensentation.features.favorites.FavoritesViewModel
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    @RelaxedMockK
    lateinit var getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase

    @InjectMockKs
    lateinit var favoritesViewModel: FavoritesViewModel

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
    fun `Get all favorites with success`() = runTest {
        // Given
        val favMoviesList = listOf(Movie())
        coEvery { getFavoriteMoviesUseCase() } coAnswers { flow { emit(favMoviesList) } }
        // when
        favoritesViewModel.getFavoriteMovies()
        // Then
        favoritesViewModel.favoriteMoviesState.test {
            awaitItem()
            val list = awaitItem()
            assertEquals(favMoviesList[0].title, list[0].title)
        }
    }

    @Test
    fun `Get all favorites return empty list`() = runTest {
        // Given
        coEvery { getFavoriteMoviesUseCase() } coAnswers { flow { emit(emptyList()) } }
        // when
        favoritesViewModel.getFavoriteMovies()
        // Then
        favoritesViewModel.favoriteMoviesState.test {
            val list = awaitItem()
            assert(list.isEmpty())
        }
    }
}
