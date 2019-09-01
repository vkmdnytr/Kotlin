package com.example.movie.entity.model.data.movie

data class MovieResultsItem(
    var poster_path: String?,
    val adult: Boolean,
    val overview: String,
    val genre_ids: List<Int>,
    val id: Int,
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    val title: String,
    var vote_average: Float=0f,
    val release_date: String
)