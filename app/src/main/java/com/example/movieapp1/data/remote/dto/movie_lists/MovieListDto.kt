package com.example.movieapp1.data.remote.dto.movie_lists

import com.example.movieapp1.domain.model.Movies

data class MovieListDto(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)
fun MovieListDto.getmovies(): List<Movies>{
    return results.map { result ->
        Movies(result.adult,result.id,result.original_title,result.poster_path,result.release_date,result.vote_average)
    }
}