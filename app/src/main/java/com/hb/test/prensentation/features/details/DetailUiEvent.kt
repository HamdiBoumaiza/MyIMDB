package com.hb.test.prensentation.features.details

import com.hb.test.domain.model.Movie

sealed interface DetailUiEvent {
    data class AddToFavMovies(val movie: Movie) : DetailUiEvent
    data class DeleteFavoriteMovie(val id: Int) : DetailUiEvent
}
