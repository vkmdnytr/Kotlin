package com.example.movie.entity.rest

import DetailResultItem
import com.example.movie.entity.model.data.movie.MovieListResultItem
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*


interface ServiceInterface {

    @GET(Urls.NOW_PLAYING_LIST_URL)
    fun getNowPlayingData(): Deferred<Response<MovieListResultItem>>
    @GET(Urls.UP_COMING_LIST_URL)
    fun getUpComingData(): Deferred<Response<MovieListResultItem>>
    @GET(Urls.TOP_RATED_LIST_URL)
    fun getTopRatedData(): Deferred<Response<MovieListResultItem>>
    @GET(Urls.SEARCH_URL)
    fun getSearchMovieResultData(@Query("query") query: String,@Query("page") page: Int=1,@Query("include_adult") include_adult: Boolean=false): Deferred<Response<MovieListResultItem>>
    @GET(Urls.DETAIL_URL)
    fun getDetailMovieResultData(@Path("movie_id") movie_id: Int,@Query("api_key") api_key: String="a174c811323155563ee8a95099adc2fb", @Query("language") language: String="en-US", @Query("include_adult") include_adult: Boolean=false): Deferred<Response<DetailResultItem>>

}

