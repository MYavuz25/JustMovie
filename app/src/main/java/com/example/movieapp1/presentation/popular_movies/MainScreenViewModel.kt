package com.example.movieapp1.presentation.popular_movies

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp1.domain.use_case.get_genre_filtered_list.GetFilteredWithGenreUseCase
import com.example.movieapp1.domain.use_case.get_now_playings.GetNowPlayingMoviesUseCase
import com.example.movieapp1.domain.use_case.get_popular_movies.GetPopularMoviesUseCase
import com.example.movieapp1.domain.use_case.get_trend_movie_day.GetTrendDayMovieUseCase
import com.example.movieapp1.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getFilteredWithGenreUseCase: GetFilteredWithGenreUseCase,
    private val getTrendDayMovieUseCase: GetTrendDayMovieUseCase
) :ViewModel() {
    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    init {
        getNowPlayingMovies()
        getPopularMovies()
        getMovieListAccordingToGenre(12)
        getTrendMovieDay()
        getMovieListAccordingToGenre(10751)
    }

    private fun getPopularMovies() {
        getPopularMoviesUseCase.getPopularMovies().onEach {
            when (it) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(error = it.message ?: "Error")
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(popularMovies = it.data, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getNowPlayingMovies() {
        getNowPlayingMoviesUseCase.getNowPlayingMovies().onEach {
            when (it) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(error = it.message ?: "Error")
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(nowPlayingMovies = it.data, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMovieListAccordingToGenre(genreId: Int) {
        getFilteredWithGenreUseCase.getMoviesAccordingToGenre(genreId).onEach {
            when (it) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(error = it.message ?: "Error")
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    when(genreId){
                        10751 ->{
                            _state.value = _state.value.copy(movieListAccordingToFamilyGenre = it.data, isLoading = false)
                        }
                        12 ->{
                            _state.value = _state.value.copy(movieListAccordingToAdventureGenre = it.data, isLoading = false)
                        }
                    }

                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTrendMovieDay() {
        getTrendDayMovieUseCase.getTrendDayMovie().onEach {
            when (it) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(error = it.message ?: "Error")
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(dailyTrendMovieList = it.data, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }
}
