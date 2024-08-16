package com.example.movieapp1.domain.model

data class Movies(
    val adult: Boolean,
    val id: Int,
    val original_title: String,
    val poster_path: String?,
    val release_date: String,
    val vote_average: Double,
)
