package com.example.movieapp1.domain.use_case.get_movie_detail

import com.example.movieapp1.data.remote.dto.movie_detail.toMovie
import com.example.movieapp1.domain.model.MovieDetail
import com.example.movieapp1.domain.repository.MovieRepository
import com.example.movieapp1.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val repository: MovieRepository){
    fun getMovieDetail(movieId:Int) :Flow<Resource<MovieDetail>> = flow {
        try {
            emit(Resource.Loading())
            val movie = repository.getMovieDetail(movieId)
            emit(Resource.Success(movie.toMovie()))
        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage?:"ERROR !!"))
        }
    }
}