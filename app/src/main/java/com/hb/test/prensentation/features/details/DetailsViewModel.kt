package com.hb.test.prensentation.features.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.hb.test.data.Resource
import com.hb.test.domain.model.Movie
import com.hb.test.domain.usecases.DeleteFavoriteMovieUseCase
import com.hb.test.domain.usecases.GetFavoriteMovieByIdUseCase
import com.hb.test.domain.usecases.GetMovieCastUseCase
import com.hb.test.domain.usecases.GetMovieDetailsUseCase
import com.hb.test.domain.usecases.GetSimilarMoviesUseCase
import com.hb.test.domain.usecases.InsertFavoriteMovieUseCase
import com.hb.test.utils.mapError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val insertFavoriteMovieUseCase: InsertFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val getFavoriteMovieByIdUseCase: GetFavoriteMovieByIdUseCase,
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<DetailsScreenUIState>(DetailsScreenUIState.Loading)
    val movieDetails get() = _movieDetails.asStateFlow()

    private var _movieCast =
        MutableStateFlow<DetailsScreenCastUIState>(DetailsScreenCastUIState.Loading)
    val movieCast get() = _movieCast.asStateFlow()

    private val _similarMovies: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(value = PagingData.empty())
    val similarMovies get() = _similarMovies.asStateFlow()

    private val _isFavoriteMovie: MutableStateFlow<Boolean> = MutableStateFlow(value = true)
    val isFavoriteMovie get() = _isFavoriteMovie.asStateFlow()

    fun setMovieId(movieId: Int) {
        getMovieDetails(movieId)
        getMovieCast(movieId)
        getSimilarMovies(movieId)
        checkFavMovies(movieId)
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailsUseCase.invoke(movieId).collect {
                _movieDetails.value = when (it) {
                    is Resource.Error -> DetailsScreenUIState.Error(it.throwable.mapError())
                    is Resource.Success -> DetailsScreenUIState.Success(it.data)
                    Resource.Loading -> DetailsScreenUIState.Loading
                }
            }
        }
    }

    fun getMovieCast(movieId: Int) {
        viewModelScope.launch {
            getMovieCastUseCase(movieId).collect {
                _movieCast.value = when (it) {
                    is Resource.Error -> DetailsScreenCastUIState.Error(it.throwable.mapError())
                    is Resource.Success -> DetailsScreenCastUIState.Success(it.data)
                    Resource.Loading -> DetailsScreenCastUIState.Loading
                }
            }
        }
    }

    fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            getSimilarMoviesUseCase.invoke(movieId).collect { _similarMovies.value = it }
        }
    }

    fun checkFavMovies(id: Int) {
        viewModelScope.launch {
            getFavoriteMovieByIdUseCase(id).collect { _isFavoriteMovie.value = (it != null) }
        }
    }

    fun onEvent(uiEvent: DetailUiEvent) {
        when (uiEvent) {
            is DetailUiEvent.AddToFavMovies -> addToFavMovies(uiEvent.movie)
            is DetailUiEvent.DeleteFavoriteMovie -> deleteFavoriteMovie(uiEvent.id)
        }
    }

    private fun addToFavMovies(movie: Movie) {
        viewModelScope.launch { insertFavoriteMovieUseCase(movie) }
    }

    private fun deleteFavoriteMovie(id: Int) {
        viewModelScope.launch { deleteFavoriteMovieUseCase(id) }
    }
}
