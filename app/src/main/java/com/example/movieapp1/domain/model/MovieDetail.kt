package com.example.movieapp1.domain.model

import com.example.movieapp1.data.remote.dto.movie_detail.Genre
import com.example.movieapp1.data.remote.dto.movie_detail.ProductionCompany
import com.example.movieapp1.data.remote.dto.movie_detail.ProductionCountry
import com.example.movieapp1.data.remote.dto.movie_detail.SpokenLanguage

data class MovieDetail (
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)