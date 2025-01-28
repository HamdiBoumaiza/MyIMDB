package com.hb.test.repo

import app.cash.turbine.test
import com.google.gson.Gson
import com.hb.test.data.RemoteApi
import com.hb.test.data.Resource
import com.hb.test.data.dto.MovieDto
import com.hb.test.data.repo.RemoteFilmRepositoryImpl
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesRepositoryWebSererMockTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: RemoteApi

    private val client = OkHttpClient.Builder().build()

    private lateinit var remoteFilmRepositoryImpl: RemoteFilmRepositoryImpl

    @Before
    fun setup() {
        mockWebServer = MockWebServer()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RemoteApi::class.java)

        remoteFilmRepositoryImpl = RemoteFilmRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `check if 500 is thrown`() = runTest {
        val response = MockResponse()
            .setBody("Server messed this up!")
            .setResponseCode(500)

        mockWebServer.enqueue(response)

        val flow = remoteFilmRepositoryImpl.getMovieDetails(1)
        flow.test {
            val state = awaitItem()
            assertEquals(state, Resource.Loading)
            val state2 = awaitItem()
            assertTrue(state2 is Resource.Error)
            assertTrue((state2 as Resource.Error).throwable is HttpException)
            awaitComplete()
        }
    }

    @Test
    fun `check if call is successful`() = runTest {
        val response = MockResponse()
            .setBody(Gson().toJson(mockedMovie))
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val flow = remoteFilmRepositoryImpl.getMovieDetails(1)
        flow.test {
            val state = awaitItem()
            assertEquals(state, Resource.Loading)
            val state2 = awaitItem()
            assertTrue(state2 is Resource.Success)
            assertTrue((state2 as Resource.Success).data.title == mockedMovie.title)
            awaitComplete()
        }
    }

    companion object {
        val mockedMovie = MovieDto(
            backdropPath = "",
            posterPath = "",
            genreDtos = emptyList(),
            id = 1,
            overview = "",
            releaseDate = "",
            title = "YES",
            voteAverage = 1.1,
            voteCount = 1
        )
    }
}
