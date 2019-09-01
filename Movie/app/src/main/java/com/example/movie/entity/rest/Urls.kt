package com.example.movie.entity.rest

object Urls{

        // SERVICE URL
        const val IMAGE_URL="https://image.tmdb.org/t/p/w600_and_h900_bestv2"
        const val NOW_PLAYING_LIST_URL = "movie/now_playing?api_key=a174c811323155563ee8a95099adc2fb&language=en-US&page=1"
        const val TOP_RATED_LIST_URL = "movie/top_rated?api_key=a174c811323155563ee8a95099adc2fb&language=en-US&page=1"
        const val UP_COMING_LIST_URL ="movie/upcoming?api_key=a174c811323155563ee8a95099adc2fb&language=en-US&page=1"
        const val SEARCH_URL ="search/movie?api_key=a174c811323155563ee8a95099adc2fb&language=en-US"
        const val DETAIL_URL ="movie/{movie_id}"
}
