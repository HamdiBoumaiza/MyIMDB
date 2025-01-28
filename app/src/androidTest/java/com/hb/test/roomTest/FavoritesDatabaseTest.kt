package com.hb.test.roomTest

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hb.test.data.dto.MovieDto
import com.hb.test.data.local.FavoriteMoviesDatabase
import com.hb.test.data.local.dao.FavoriteMovieDao
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class FavoritesDatabaseTest : TestCase() {
    // get reference to the NoteDatabase and NoteDao class
    private lateinit var db: FavoriteMoviesDatabase
    private lateinit var dao: FavoriteMovieDao

    // Override function setUp() and annotate it with @Before.
    // The @Before annotation makes sure fun setupDatabase() is executed before each class.
    // The function then creates a database using Room.inMemoryDatabaseBuilder which creates
    // a database in RAM instead of the persistence storage. This means the database will be
    // cleared once the process is killed.
    @Before
    public override fun setUp() {
        // get context -- since this is an instrumental test it requires
        // context from the running application
        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FavoriteMoviesDatabase::class.java
        ).build()
        dao = db.getFavoriteMovieDetailDao()
    }

    // Override function closeDb() and annotate it with @After.
    // @After annotation means our closing function will be called every-time after
    // executing test cases. This function will be called at last when this test class is called.
    @After
    fun closeDb() {
        db.close()
    }

    // create a test function and annotate it with @Test
    // here we are first adding an item to the db and then checking if that item
    // is present in the db -- if the item is present then our test cases pass
    @Test
    fun insertNote_returnsTrue() = runBlocking {
        val movie = mockedMovie
        dao.insert(movie)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            dao.getAllMovieDetails().collect {
                assertEquals(it.first(), movie)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun getMovieById_returnsTrue() = runBlocking {
        // insert movie
        val movie = mockedMovie
        dao.insert(movie)

        val job = async(Dispatchers.IO) {
            dao.getMovieDetailById(movie.id!!).collect {
                assertEquals(it, movie)
            }
        }
        job.cancelAndJoin()
    }

    @Test
    fun delete_returnsTrue() = runBlocking {
        val movie1 = mockedMovie
        val movie2 = mockedMovie.copy(id = 2)
        dao.insert(movie1)
        dao.insert(movie2)

        dao.deleteMovieDetailById(1)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            dao.getAllMovieDetails().collect {
                assertEquals(it.size, 1)
                assertEquals(it.first(), movie2)
                assertNotSame(it.first(), movie1)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    companion object {
        val mockedMovie = MovieDto(
            backdropPath = "",
            posterPath = "",
            genreDtos = emptyList(),
            id = 1,
            overview = "",
            releaseDate = "",
            title = "",
            voteAverage = 1.1,
            voteCount = 1
        )
    }
}
