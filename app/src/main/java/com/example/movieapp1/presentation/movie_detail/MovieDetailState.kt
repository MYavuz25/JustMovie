package com.example.movieapp1.presentation.movie_detail

import com.example.movieapp1.data.remote.dto.movie_watch_providers.WatchProvidersResponse
import com.example.movieapp1.domain.model.MovieDetail
import com.example.movieapp1.domain.model.Movies


data class MovieDetailState (
    val isLoading:Boolean=false,
    val movie: MovieDetail? = null,
    val error:String ="",
    val similarMoviesList:List<Movies>? = null,
    val isSimilarLoading: Boolean=false,
    val similarError:String ="",
    val genreMovieList:List<Movies>?=null,
    val watchProviders: WatchProvidersResponse? = null
)