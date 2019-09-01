package com.example.movie.ui.toprated

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

class TopRatedViewModel: ViewModel(){


    private var topRatedLiveData = MutableLiveData<Results<MovieListResultItem>>()
    private val isViewProgressBar= MutableLiveData<Int>()

    //LIVE DATA
    fun getTopRatedLiveData():MutableLiveData<Results<MovieListResultItem>> {
        return topRatedLiveData
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
    fun getTopRatedListData() {

        launchDataLoad{
            suspendGetTopRatedData()
        }
    }
    suspend fun  suspendGetTopRatedData() {
        val result = movieService.getTopRatedData().await()
        if (result.isSuccessful) {
            result.body()?.let { movieItem ->
                topRatedLiveData.value = Results.Success(movieItem)

            }
        } else {
            result
        }
    }
    fun getSearchTopRatedListData(query:String) {

        launchDataLoad{

            suspendGetSearchData(query)

        }
    }
    suspend fun suspendGetSearchData(query: String) {
        val result = movieService.getSearchMovieResultData(query).await()
        if (result.isSuccessful) {
            result.body()?.let { MovieItem ->
                topRatedLiveData.value = Results.Success(MovieItem)

            }
        } else {
            result
        }
    }

}