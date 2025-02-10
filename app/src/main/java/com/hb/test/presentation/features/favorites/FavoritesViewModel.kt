package com.hb.test.presentation.features.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hb.test.domain.model.Movie
import com.hb.test.domain.usecases.GetFavoriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModel() {

    private var _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMoviesState = _favoriteMovies.asStateFlow()

    init {
        getFavoriteMovies()
    }

    fun getFavoriteMovies() {
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collect {
                _favoriteMovies.value = it
            }
        }
    }
}
