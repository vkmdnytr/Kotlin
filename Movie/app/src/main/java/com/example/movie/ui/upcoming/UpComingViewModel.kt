package com.example.movie.ui.upcoming

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

class UpComingViewModel: ViewModel(){


    private val movieLiveData = MutableLiveData<Results<MovieListResultItem>>()
    private val isViewProgressBar= MutableLiveData<Int>()

    fun getUpComingLiveData(): MutableLiveData<Results<MovieListResultItem>> {
        return movieLiveData
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


    fun getUpComingListData() {

        launchDataLoad{

            suspendGetUpComing()

        }
    }
    suspend fun suspendGetUpComing() {
        val result = movieService.getUpComingData().await()
        if (result.isSuccessful) {
            result.body()?.let { MovieItem ->
                movieLiveData.value = Results.Success(MovieItem)

            }
        } else {
            result
        }
    }
    fun getSearchUpComingListData(query:String) {

        launchDataLoad{

            suspendSearchUpComing(query)

        }
    }
    suspend fun suspendSearchUpComing(query: String) {
        val result = movieService.getSearchMovieResultData(query).await()
        if (result.isSuccessful) {
            result.body()?.let { MovieItem ->
                movieLiveData.value = Results.Success(MovieItem)
            }
        } else {
            result
        }
    }


}