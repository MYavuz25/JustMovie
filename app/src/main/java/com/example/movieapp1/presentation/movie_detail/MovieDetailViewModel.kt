package com.example.movieapp1.presentation.movie_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp1.domain.use_case.get_genre_filtered_list.GetFilteredWithGenreUseCase
import com.example.movieapp1.domain.use_case.get_movie_detail.GetMovieDetailUseCase
import com.example.movieapp1.domain.use_case.get_similar_movies.GetSimilarMoviesUseCase
import com.example.movieapp1.domain.use_case.get_watch_providers.GetWatchProvidersUseCase
import com.example.movieapp1.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getWatchProvidersUseCase: GetWatchProvidersUseCase,
    private val getFilteredWithGenreUseCase: GetFilteredWithGenreUseCase
):ViewModel() {
    private val _state= mutableStateOf(MovieDetailState())
    val state : State<MovieDetailState> = _state

    fun getMovieDetail(movieId: Int) {
        getMovieDetailUseCase.getMovieDetail(movieId).onEach {
            when (it) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(error = it.message ?: "ERROR", isLoading = false)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(movie = it.data, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSimilarMovies(movieId: Int) {
        getSimilarMoviesUseCase.getSimilarMovies(movieId).onEach {
            when (it) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(similarError = it.message ?: "ERROR", isSimilarLoading = false)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isSimilarLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(similarMoviesList = it.data, isSimilarLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getFilteredWithGenre(genreId:String){
        getFilteredWithGenreUseCase.getMoviesAccordingToGenre(genreId).onEach {
            when(it){
                is Resource.Error -> {
                    _state.value = _state.value.copy(similarError = it.message ?: "ERROR", isSimilarLoading = false)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isSimilarLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(genreMovieList = it.data, isSimilarLoading = false)
                }
            }

        }.launchIn(viewModelScope)
    }
    fun getWatchProviders(movieId: Int){
        getWatchProvidersUseCase.getWatchProviders(movieId).onEach {
            when(it){
                is Resource.Error -> {
                    _state.value = _state.value.copy(similarError = it.message ?: "ERROR", isSimilarLoading = false)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isSimilarLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(watchProviders = it.data, isSimilarLoading = false)
                }
            }

        }.launchIn(viewModelScope)
    }



}