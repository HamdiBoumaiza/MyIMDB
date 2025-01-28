package com.hb.test.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hb.test.data.RemoteApi
import com.hb.test.domain.mappers.mapToMovie
import com.hb.test.domain.model.Movie
import retrofit2.HttpException
import java.io.IOException

class PopularFilmSource(private val api: RemoteApi) :
    PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val popularFilms = api.getPopularMovies(page = nextPage)
            LoadResult.Page(
                data = popularFilms.results.map { it.mapToMovie() },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (popularFilms.results.isEmpty()) null else popularFilms.page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}
