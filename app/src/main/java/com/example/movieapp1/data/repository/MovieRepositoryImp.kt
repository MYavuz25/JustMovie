package com.example.movieapp1.data.repository

import com.example.movieapp1.data.remote.dto.movie_detail.MovieDetailDTO
import com.example.movieapp1.data.remote.dto.movie_lists.MovieListDto
import com.example.movieapp1.data.remote.retrofit.MovieApi
import com.example.movieapp1.domain.repository.MovieRepository
import com.example.movieapp1.util.Constants.API_KEY
import java.util.Locale
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val api: MovieApi): MovieRepository {
    val language=Locale.getDefault().language
    override suspend fun getMovieDetail(movieId: Int): MovieDetailDTO {
        return api.getMovieDetail(movieId,API_KEY,language)
    }

    override suspend fun getPopularMovies(): MovieListDto {
        return api.getPopularMovie(API_KEY,language)
    }

    override suspend fun getNowPlayingMovies(): MovieListDto {
        return api.getNowPlayingMovies(API_KEY,language)
    }

    override suspend fun getFilteredMovies(genreId:Int): MovieListDto {
        return api.getFilteredMovies(API_KEY,language,genreId)
    }

    override suspend fun getTrendDayMovie(): MovieListDto {
        return api.getTrendDayMovie(API_KEY,language)
    }

    override suspend fun getSearchingMovies(search:String): MovieListDto {
        return api.getSearchedMovies(API_KEY,language, searchString = search)
    }
}