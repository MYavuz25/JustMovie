package com.example.movieapp1.presentation.search_screen

import androidx.annotation.FloatRange
import com.example.movieapp1.domain.model.Movies

data class SearchScreenState(
    val isLoading: Boolean = false,
    var movies: List<Movies>? = null,
    val error: String = "",
    val currentPage: Int = 1
)
