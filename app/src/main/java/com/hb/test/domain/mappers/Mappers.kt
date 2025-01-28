package com.hb.test.domain.mappers

import com.hb.test.data.dto.CastDto
import com.hb.test.data.dto.GenreDto
import com.hb.test.data.dto.MovieDto
import com.hb.test.domain.model.Cast
import com.hb.test.domain.model.Genre
import com.hb.test.domain.model.Movie
import com.hb.test.utils.Constants.BASE_POSTER_IMAGE_URL

fun GenreDto.mapToGenre() = Genre(
    id = this.id ?: 0,
    name = this.name ?: "",
)

fun Genre.mapToGenre() = GenreDto(
    id = this.id,
    name = this.name,
)

fun CastDto.mapToCast() = Cast(
    name = this.name ?: "",
    imageUrl = BASE_POSTER_IMAGE_URL.plus(this.profilePath) ?: "",
)

fun MovieDto.mapToMovie() = Movie(
    backdropPath = if (backdropPath?.startsWith(BASE_POSTER_IMAGE_URL) == true) backdropPath
    else BASE_POSTER_IMAGE_URL.plus(this.backdropPath),
    imageUrl = if (posterPath?.startsWith(BASE_POSTER_IMAGE_URL) == true) posterPath
    else BASE_POSTER_IMAGE_URL.plus(this.posterPath),
    genres = this.genreDtos?.map { it.mapToGenre() } ?: emptyList(),
    id = this.id ?: 0,
    overview = this.overview ?: "",
    releaseDate = this.releaseDate ?: "",
    title = this.title ?: "",
    voteAverage = this.voteAverage ?: 0.0,
    voteCount = this.voteCount ?: 0
)

fun Movie.mapToMovieDto() = MovieDto(
    backdropPath = this.backdropPath,
    posterPath = imageUrl,
    genreDtos = this.genres.map { it.mapToGenre() },
    id = this.id,
    overview = this.overview,
    releaseDate = this.releaseDate,
    title = this.title,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount
)
