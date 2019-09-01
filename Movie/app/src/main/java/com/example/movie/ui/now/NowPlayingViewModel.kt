package com.example.nowPlaying.ui.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.entity.model.data.movie.MovieListResultItem
import com.example.movie.entity.model.sealed.Results
import com.example.movie.entity.rest.movieService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NowPlayingViewModel: ViewModel(){


    private val nowPlayingLiveData = MutableLiveData<Results<MovieListResultItem>>()
    private val isViewProgressBar= MutableLiveData<Int>()

    fun getNowPlayingLiveData(): MutableLiveData<Results<MovieListResultItem>> {
        return nowPlayingLiveData
    }
    fun getProgresBarStateLiveData(): LiveData<Int> {
        return isViewProgressBar
    }

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return uiScope.launch{
            try {
                isViewProgressBar.value = View.VISIBLE
                block()
            } catch (error: Error) {
                isViewProgressBar.value = View.GONE
            } finally {
                isViewProgressBar.value = View.GONE
            }
        }
    }
    //Local file read
    fun getNowPlayData() {

        launchDataLoad{

            suspendGetNowPlayingData()

        }
    }

     suspend fun suspendGetNowPlayingData() {
        val result = movieService.getNowPlayingData().await()
        if (result.isSuccessful) {
            result.body()?.let { MovieItem ->
                nowPlayingLiveData.value = Results.Success(MovieItem)

            }
        } else {
            result
        }
    }

    fun getSearchNowPlayingListData(query:String) {

        launchDataLoad{

            suspendSearchNowPlaying(query)

        }
    }

     suspend fun suspendSearchNowPlaying(query: String) {
        val result = movieService.getSearchMovieResultData(query).await()
        if (result.isSuccessful) {
            result.body()?.let { MovieItem ->
                nowPlayingLiveData.value = Results.Success(MovieItem)

            }
        } else {
            result
        }
    }


}