package com.example.movie.ui.upcoming

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito


@RunWith(JUnit4::class)
class UpComingViewModelTest{

    var upComingViewModel: UpComingViewModel? = null
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        upComingViewModel = UpComingViewModel()
    }
    private fun testGetUpComingLiveDataNull() {
        MainScope().launch {
            Mockito.`when`(upComingViewModel?.suspendGetUpComing())
            assertNotNull(upComingViewModel?.getUpComingLiveData())
            assertTrue(upComingViewModel?.getUpComingLiveData()!!.hasObservers())
        }
    }
    private fun testGetSearchUpComingLiveDataNull() {
        MainScope().launch {
            Mockito.`when`(upComingViewModel?.suspendSearchUpComing(""))
            assertNotNull(upComingViewModel?.getUpComingLiveData())
            assertTrue(upComingViewModel?.getUpComingLiveData()!!.hasObservers())
        }
    }
    @Test
    fun testGetUpComingNull() = testDispatcher.runBlockingTest {
        testGetUpComingLiveDataNull()
    }
    @Test
    fun testGetSearchUpComingNull() = testDispatcher.runBlockingTest {
        testGetSearchUpComingLiveDataNull()
    }

}