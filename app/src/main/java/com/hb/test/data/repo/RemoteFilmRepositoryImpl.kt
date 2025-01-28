package com.hb.test.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hb.test.data.RemoteApi
import com.hb.test.data.Resource
import com.hb.test.data.paging.PopularFilmSource
import com.hb.test.data.paging.SearchFilmSource
import com.hb.test.data.paging.SimilarFilmSource
import com.hb.test.data.paging.TopRatedFilmSource
import com.hb.test.data.paging.TrendingFilmSource
import com.hb.test.data.paging.UpcomingFilmSource
import com.hb.test.domain.mappers.mapToCast
import com.hb.test.domain.mappers.mapToMovie
import com.hb.test.domain.model.Cast
import com.hb.test.domain.model.Movie
import com.hb.test.domain.repo.RemoteFilmRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteFilmRepositoryImpl @Inject constructor(private val api: RemoteApi) :
    RemoteFilmRepositoryInterface {

    override fun searchFilms(searchParam: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = { SearchFilmSource(api = api, searchParam) }
        ).flow
    }

    override fun getTrendingFilms(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = { TrendingFilmSource(api = api) }
        ).flow
    }

    override fun getPopularFilms(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = { PopularFilmSource(api = api) }
        ).flow
    }

    override fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = { TopRatedFilmSource(api = api) }
        ).flow
    }

    override fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = { UpcomingFilmSource(api = api) }
        ).flow
    }

    override suspend fun getMovieDetails(filmId: Int): Flow<Resource<Movie>> = flow {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(api.getMovieDetails(movieId = filmId).mapToMovie()))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun getMovieCast(filmId: Int): Flow<Resource<List<Cast>>> = flow {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(api.getMovieCast(filmId = filmId).castDtoResult.map { it.mapToCast() }))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override fun getSimilarMovies(movieId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = { SimilarFilmSource(api = api, movieId = movieId) }
        ).flow
    }
}
