package com.hb.test.presentation.features.details

import com.hb.test.domain.model.Movie

sealed interface DetailUiEvent {
    data class SetMovieId(val movieId: Int) : DetailUiEvent
    data class AddToFavMovies(val movie: Movie) : DetailUiEvent
    data class DeleteFavoriteMovie(val id: Int) : DetailUiEvent
}
