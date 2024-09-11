package com.example.movieapp1.presentation.home_screen

import com.example.movieapp1.domain.model.Movies

data class MainScreenState(
    val isLoading: Boolean = false,
    val popularMovies: List<Movies>? = null,
    val nowPlayingMovies: List<Movies>? = null,
    val movieListAccordingToAdventureGenre: List<Movies>? = null,
    val dailyTrendMovieList: List<Movies>? = null,
    val error: String = "",
    val movieListAccordingToFamilyGenre: List<Movies>? = null
)
