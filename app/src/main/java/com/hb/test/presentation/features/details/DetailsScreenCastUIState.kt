package com.hb.test.presentation.features.details

import com.hb.test.domain.model.Cast

sealed class DetailsScreenCastUIState {
    data object Loading : DetailsScreenCastUIState()
    data class Success(val listCast: List<Cast>) : DetailsScreenCastUIState()
    data class Error(val messageRes: Int) : DetailsScreenCastUIState()
}
