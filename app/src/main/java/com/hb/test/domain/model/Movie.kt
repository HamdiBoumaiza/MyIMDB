package com.hb.test.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val backdropPath: String? = "",
    val imageUrl: String = "",
    val genres: List<Genre> = emptyList(),
    val id: Int = 0,
    val originalLanguage: String = "",
    val overview: String = "",
    val releaseDate: String = "",
    val title: String = "",
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0
) : Parcelable
