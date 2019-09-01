package com.example.movie.ui.toprated

import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`


@RunWith(JUnit4::class)
class TopRatedViewModelTest {

    var topRatedViewModel: TopRatedViewModel? = null
    private val testDispatcher = TestCoroutineDispatcher()



    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        topRatedViewModel = TopRatedViewModel()
    }
    private fun testGetTopRatedLiveDataNull() {
        MainScope().launch {
            `when`(topRatedViewModel?.suspendGetTopRatedData())
            assertNotNull(topRatedViewModel?.getTopRatedLiveData())
            assertTrue(topRatedViewModel?.getTopRatedLiveData()!!.hasObservers())
        }
    }
    private fun testGetSearchTopRatedLiveDataNull() {
        MainScope().launch {
            `when`(topRatedViewModel?.suspendGetSearchData(""))
            assertNotNull(topRatedViewModel?.getTopRatedLiveData())
            assertTrue(topRatedViewModel?.getTopRatedLiveData()!!.hasObservers())
        }
    }
    @Test
    fun testGetTopRatedNull() = testDispatcher.runBlockingTest {
        testGetTopRatedLiveDataNull()
    }
    @Test
    fun testGetSearchTopRatedNull() = testDispatcher.runBlockingTest {
        testGetSearchTopRatedLiveDataNull()
    }

}