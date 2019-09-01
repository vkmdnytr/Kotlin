package com.example.movie.entity.model.data.favorite

data class LocalFavoriteMovie(
    var poster_path: String?,
    val overview: String,
    val id: Int,
    val vote_count: Int,
    val video: Boolean,
    val title: String,
    var vote_average: Float=0f,
    val release_date: String
)