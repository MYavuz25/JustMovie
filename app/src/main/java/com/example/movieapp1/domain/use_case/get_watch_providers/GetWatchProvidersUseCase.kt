package com.example.movieapp1.domain.use_case.get_watch_providers
import com.example.movieapp1.data.remote.dto.movie_watch_providers.WatchProvidersResponse
import com.example.movieapp1.domain.repository.MovieRepository
import com.example.movieapp1.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWatchProvidersUseCase @Inject constructor(private val repository: MovieRepository) {
    fun getWatchProviders(movieId: Int) : Flow<Resource<WatchProvidersResponse>> = flow {
        try {
            emit(Resource.Loading())
            val watchProviders = repository.getWatchProviders(movieId)
            if (watchProviders.results.isNotEmpty()) {
                emit(Resource.Success(watchProviders))
            } else {
                emit(Resource.Error("ERROR: No watch providers available"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "ERROR: Unable to fetch watch providers"))
        }
    }
}
