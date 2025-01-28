package com.hb.test.api

import com.google.gson.Gson
import com.hb.test.data.RemoteApi
import com.hb.test.data.dto.MovieDto
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

class ApiWebSererMockTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: RemoteApi

    private val client = OkHttpClient.Builder().build()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RemoteApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test(expected = HttpException::class)
    fun `check if 500 is thrown`() = runTest {
        val response = MockResponse()
            .setBody("Server messed this up!")
            .setResponseCode(500)

        mockWebServer.enqueue(response)
        apiService.getMovieDetails(1)
    }

    @Test
    fun `check if call is successful`() = runTest {
        val response = MockResponse()
            .setBody(Gson().toJson(mockedMovie))
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val result = apiService.getMovieDetails(1)
        assertEquals(result, mockedMovie)

        val request = mockWebServer.takeRequest()
        assertEquals("/movie/1?language=en", request.path)
        assertEquals("GET", request.method)
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
