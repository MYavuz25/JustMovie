package com.example.movieapp1.presentation.search_screen

import com.example.movieapp1.domain.model.Movies

data class SearchScreenState (
    val isLoading:Boolean=false,
    val movies: List<Movies>? = null,
    val error:String =""
)