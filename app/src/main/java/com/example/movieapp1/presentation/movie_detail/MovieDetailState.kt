package com.example.movieapp1.presentation.movie_detail

import com.example.movieapp1.domain.model.MovieDetail


data class MovieDetailState (
    val isLoading:Boolean=false,
    val movie: MovieDetail? = null,
    val error:String =""
)