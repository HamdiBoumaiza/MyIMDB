package com.hb.test.data.response

import com.google.gson.annotations.SerializedName
import com.hb.test.data.dto.MovieDto

data class FilmResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
