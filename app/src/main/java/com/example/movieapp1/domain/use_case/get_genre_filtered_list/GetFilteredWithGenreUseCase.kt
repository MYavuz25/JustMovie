package com.example.movieapp1.domain.use_case.get_genre_filtered_list

import com.example.movieapp1.data.remote.dto.movie_lists.getmovies
import com.example.movieapp1.domain.model.Movies
import com.example.movieapp1.domain.repository.MovieRepository
import com.example.movieapp1.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFilteredWithGenreUseCase @Inject constructor(private val repository: MovieRepository) {
    fun getMoviesAccordingToGenre(genreId:Int) : Flow<Resource<List<Movies>>> = flow{
        try {
            emit(Resource.Loading())
            val movies=repository.getFilteredMovies(genreId)
            if (movies.total_results!=0){
                emit(Resource.Success(movies.getmovies()))
            }else{
                emit(Resource.Error("ERROR NO RESULT"))
            }
        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage?:"ERROR"))
        }
    }
}