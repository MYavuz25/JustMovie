package com.example.movieapp1.presentation.search_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp1.domain.use_case.get_serched_movies.GetSearchedMoviesUseCase
import com.example.movieapp1.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getSearchedMoviesUseCase: GetSearchedMoviesUseCase
) :ViewModel(){
    private val _state = mutableStateOf(SearchScreenState())
    val state: State<SearchScreenState> = _state
    private var job:Job?=null
    init {
        getSearchedMovies("Star Wars")
    }

     private fun getSearchedMovies(search:String){
         job?.cancel()
         job=getSearchedMoviesUseCase.getSearchedMovies(search).onEach {
            when(it){
                is Resource.Error -> {
                    _state.value = _state.value.copy(error = it.message ?: "Error")
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(movies = it.data, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }
    fun onEvent(event: SearchScreenEvent ){
        when(event){
            is SearchScreenEvent.Search ->{
                getSearchedMovies(event.searchString)
            }
        }
    }
}