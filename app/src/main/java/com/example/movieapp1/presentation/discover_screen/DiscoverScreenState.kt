package com.example.movieapp1.presentation.discover_screen

import com.example.movieapp1.domain.model.Movies

data class DiscoverScreenState(
    val isLoading: Boolean = false,
    var movies: List<Movies>? = null,
    val error: String = "",
)