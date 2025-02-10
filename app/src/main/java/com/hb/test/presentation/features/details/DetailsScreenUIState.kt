package com.hb.test.presentation.features.details

import com.hb.test.domain.model.Movie

sealed class DetailsScreenUIState {
    data object Loading : DetailsScreenUIState()
    data class Success(val movie: Movie) : DetailsScreenUIState()
    data class Error(val messageRes: Int) : DetailsScreenUIState()
}
