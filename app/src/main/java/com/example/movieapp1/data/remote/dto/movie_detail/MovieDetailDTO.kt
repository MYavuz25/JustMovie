package com.example.movieapp1.data.remote.dto.movie_detail

import com.example.movieapp1.domain.model.MovieDetail

data class MovieDetailDTO(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,//bunu alamıyoz
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
    val videos: Videos,//bunu alamıyoz
    val vote_average: Double,
    val vote_count: Int
)

fun MovieDetailDTO.toMovie():MovieDetail{
    return MovieDetail(adult, backdrop_path, budget, genres, homepage, id, imdb_id, origin_country, original_language, original_title, overview, popularity, poster_path, production_companies, production_countries, release_date, revenue, runtime, spoken_languages, status, tagline, title, video,  vote_average, vote_count)
}