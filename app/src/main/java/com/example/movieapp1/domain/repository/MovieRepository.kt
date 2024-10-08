package com.example.movieapp1.domain.repository

import com.example.movieapp1.data.remote.dto.movie_detail.MovieDetailDTO
import com.example.movieapp1.data.remote.dto.movie_lists.MovieListDto
import com.example.movieapp1.data.remote.dto.movie_watch_providers.WatchProvidersResponse

interface MovieRepository {
    suspend fun getMovieDetail(movieId:Int): MovieDetailDTO
    suspend fun getPopularMovies(): MovieListDto
    suspend fun getNowPlayingMovies():MovieListDto
    suspend fun getFilteredMovies(genreId:String) : MovieListDto
    suspend fun getTrendDayMovie():MovieListDto
    suspend fun getSearchingMovies(search:String) : MovieListDto
    suspend fun getApplyFilteredMovies(sortBy:String?,genreIds: String?,minVote:Float?,maxVote:Float?,releaseDateGte:String?,releaseDatelte:String?,originalLanguage:String?,includeAdult:Boolean?,voteCount:Int?,page:Int?
    ):MovieListDto
    suspend fun getSimilarMovies(movieId: Int):MovieListDto
    suspend fun getWatchProviders(movieId: Int):WatchProvidersResponse
}
