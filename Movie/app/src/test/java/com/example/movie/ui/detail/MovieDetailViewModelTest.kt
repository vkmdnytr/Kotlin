package com.example.movie.ui.detail

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class MovieDetailViewModelTest{
    
    var movieDetailViewModel: MovieDetailViewModel? = null
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        movieDetailViewModel = MovieDetailViewModel()
    }
    private fun testGetMovieDetailLiveDataNull() {
        MainScope().launch {
            Mockito.`when`(movieDetailViewModel?.suspendGetMovieDetail(5))
            assertNotNull(movieDetailViewModel?.getMovieDetailLiveData())
            assertTrue(movieDetailViewModel?.getMovieDetailLiveData()!!.hasObservers())
        }
    }

    @Test
    fun testGetMovieDetailNull() = testDispatcher.runBlockingTest {
        testGetMovieDetailLiveDataNull()
    }

}