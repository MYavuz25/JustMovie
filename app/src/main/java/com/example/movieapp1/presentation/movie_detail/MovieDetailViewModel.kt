package com.example.movieapp1.presentation.movie_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp1.domain.use_case.get_movie_detail.GetMovieDetailUseCase
import com.example.movieapp1.util.Constants.MOVIE_ID
import com.example.movieapp1.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val stateHandle: SavedStateHandle
):ViewModel() {
    private val _state= mutableStateOf<MovieDetailState>(MovieDetailState())
    val state : State<MovieDetailState> = _state

    init {
        stateHandle.get<Int>(MOVIE_ID)?.let {
            getMovieDetail(it)
        }
    }

    fun getMovieDetail(movieId:Int){
        getMovieDetailUseCase.getMovieDetail(movieId).onEach {
            when(it){
                is Resource.Error ->{
                    _state.value= MovieDetailState(error = it.message?:"ERROR")
                }
                is Resource.Loading -> {
                    _state.value= MovieDetailState(true)
                }
                is Resource.Success -> {
                    _state.value= MovieDetailState(movie = it.data)
                }
            }
        }.launchIn(viewModelScope)

    }

}