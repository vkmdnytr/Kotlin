package com.example.movie.entity.model.data.movie
data class MovieListResultItem(val page:String, val dates:DateItem, var results:List<MovieResultsItem>, val total_pages:Int, val total_result:Int)