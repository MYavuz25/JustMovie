package com.example.movieapp1.data.remote.retrofit

import com.example.movieapp1.data.remote.dto.movie_detail.MovieDetailDTO
import com.example.movieapp1.data.remote.dto.movie_lists.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    //https://api.themoviedb.org/3/movie/
    //157336?language=tr&api_key=
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int, // Dinamik film ID'si
        @Query("api_key") apiKey: String, // API anahtarı
        @Query("language") language: String // Dil parametresi
    ): MovieDetailDTO

    //https://api.themoviedb.org/3/movie/popular?api_key=API_KEY&page=1
    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String, // API anahtarı
        @Query("language") language: String // Dil parametresi
    ):MovieListDto

    //https://api.themoviedb.org/3/movie/now_playing?api_key=API_KEY&language=en-US&page=1
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String, // API anahtarı
        @Query("language") language: String // Dil parametresi
    ):MovieListDto

    //https://api.themoviedb.org/3/discover/movie?api_key=API_KEY&with_genres=28
    @GET("discover/movie")
    suspend fun getFilteredMovies(
        @Query("api_key") apiKey: String, // API anahtarı
        @Query("language") language: String, // Dil parametresi
        @Query("with_genres") genreId: Int
    ):MovieListDto

    //https://api.themoviedb.org/3/trending/movie/day?api_key=API_KEY&language=tr
    @GET("trending/movie/day")
    suspend fun getTrendDayMovie(
        @Query("api_key") apiKey: String, // API anahtarı
        @Query("language") language: String // Dil parametresi
    ):MovieListDto

    //https://api.themoviedb.org/3/search/movie?api_key={API_KEY}&query={QUERY}
    @GET("search/movie")
    suspend fun getSearchedMovies(
        @Query("api_key") apiKey : String, // API anahtarı
        @Query("language") language : String, // Dil parametresi
        @Query("sort_by") sortBy: String = "popularity.desc",//Popülerliğe döre sıralar
        @Query("query") searchString : String // Aranan kelime
    ):MovieListDto

}