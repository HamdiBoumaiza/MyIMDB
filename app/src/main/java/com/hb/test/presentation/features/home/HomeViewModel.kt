package com.hb.test.presentation.features.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hb.test.data.repo.RemoteFilmRepositoryImpl
import com.hb.test.domain.model.Movie
import com.hb.test.domain.usecases.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteFilmRepositoryImpl: RemoteFilmRepositoryImpl,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private var _trendingMovies: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(value = PagingData.empty())
    val trendingMoviesState: StateFlow<PagingData<Movie>> = _trendingMovies.asStateFlow()

    private var _popularMovies: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(value = PagingData.empty())
    val popularMoviesState: StateFlow<PagingData<Movie>> = _popularMovies.asStateFlow()

    private var _topRatedMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val topRatedMoviesState: State<Flow<PagingData<Movie>>> = _topRatedMovies

    private var _upcomingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val upcomingMoviesState: State<Flow<PagingData<Movie>>> = _upcomingMovies

    private var _searchMoviesResult = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val searchMoviesResult: State<Flow<PagingData<Movie>>> = _searchMoviesResult

    init {
        getTrendingMovies()
        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
    }

    fun getTrendingMovies() {
        viewModelScope.launch {
            remoteFilmRepositoryImpl.getTrendingFilms().cachedIn(viewModelScope).collect {
                _trendingMovies.value = it
            }
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            remoteFilmRepositoryImpl.getPopularFilms().cachedIn(viewModelScope)
                .collect { _popularMovies.value = it }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            _topRatedMovies.value =
                remoteFilmRepositoryImpl.getTopRatedMovies().cachedIn(viewModelScope)
        }
    }

    fun getUpcomingMovies() {
        viewModelScope.launch {
            _upcomingMovies.value =
                remoteFilmRepositoryImpl.getUpcomingMovies().cachedIn(viewModelScope)
        }
    }

    fun searchMovies(searchParam: String) {
        viewModelScope.launch {
            _searchMoviesResult.value = searchMoviesUseCase(searchParam).cachedIn(viewModelScope)
        }
    }
}
