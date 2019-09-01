package com.example.movie.ui.now

import com.example.nowPlaying.ui.home.NowPlayingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

class NowPlayingViewModelTest{
    var nowPlayingViewModel: NowPlayingViewModel? = null
    private val testDispatcher = TestCoroutineDispatcher()



    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        nowPlayingViewModel = NowPlayingViewModel()
    }
    private fun testGetNowPlayingLiveDataNull() {
        MainScope().launch {
            `when`(nowPlayingViewModel?.suspendGetNowPlayingData())
            assertNotNull(nowPlayingViewModel?.getNowPlayingLiveData())
            assertTrue(nowPlayingViewModel?.getNowPlayingLiveData()!!.hasObservers())
        }
    }
    private fun testGetSearchNowPlayingLiveDataNull() {
        MainScope().launch {
            `when`(nowPlayingViewModel?.suspendSearchNowPlaying(""))
            assertNotNull(nowPlayingViewModel?.getNowPlayingLiveData())
            assertTrue(nowPlayingViewModel?.getNowPlayingLiveData()!!.hasObservers())
        }
    }
    @Test
    fun testGetNowPlayingNull() = testDispatcher.runBlockingTest {
        testGetNowPlayingLiveDataNull()
    }
    @Test
    fun testGetSearchNowPlayingNull() = testDispatcher.runBlockingTest {
        testGetSearchNowPlayingLiveDataNull()
    }
}