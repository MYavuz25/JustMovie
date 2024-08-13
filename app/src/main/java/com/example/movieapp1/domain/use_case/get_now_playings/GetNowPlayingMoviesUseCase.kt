package com.example.movieapp1.domain.use_case.get_now_playings

import com.example.movieapp1.data.remote.dto.movie_lists.getmovies
import com.example.movieapp1.domain.model.Movies
import com.example.movieapp1.domain.repository.MovieRepository
import com.example.movieapp1.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(private val repository: MovieRepository) {

    fun getNowPlayingMovies() : Flow<Resource<List<Movies>>> = flow {

        try {
            emit(Resource.Loading())
            val movies=repository.getNowPlayingMovies()
            if (movies.total_results!=0){
                emit(Resource.Success((movies.getmovies())))
            }else{
                emit(Resource.Error("ERROR NO RESULT"))
            }
        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage?:"ERROR"))
        }
    }

}