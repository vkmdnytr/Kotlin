package com.example.movie.ui.detail

import DetailResultItem
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.entity.model.sealed.Results
import com.example.movie.entity.rest.movieService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDetailViewModel: ViewModel(){


    private val movieDetailLiveData = MutableLiveData<Results<DetailResultItem>>()
    private val isViewProgressBar= MutableLiveData<Int>()

    //GET LIVE DATA
    fun getMovieDetailLiveData(): MutableLiveData<Results<DetailResultItem>> {
        return movieDetailLiveData
    }
    fun getProgressBarStateLiveData(): LiveData<Int> {
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

    //SERVICE
    fun getMovieDetailData(id:Int) {
        launchDataLoad{
            suspendGetMovieDetail(id)
        }
    }
    suspend fun suspendGetMovieDetail(id: Int) {
        val result = movieService.getDetailMovieResultData(id).await()
        if (result.isSuccessful) {
            result.body()?.let { MovieItem ->
                movieDetailLiveData.value = Results.Success(MovieItem)
            }
        } else {
            result
        }
    }


}