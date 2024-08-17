package com.example.movieapp1.presentation.search_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp1.domain.use_case.get_filtered_movies.GetFilteredMoviesUseCase
import com.example.movieapp1.domain.use_case.get_genre_filtered_list.GetFilteredWithGenreUseCase
import com.example.movieapp1.domain.use_case.get_serched_movies.GetSearchedMoviesUseCase
import com.example.movieapp1.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getSearchedMoviesUseCase: GetSearchedMoviesUseCase,
    private val getFilteredMoviesUseCase: GetFilteredMoviesUseCase
) :ViewModel(){
    private val _state = mutableStateOf(SearchScreenState())
    val state: State<SearchScreenState> = _state
    private var job:Job?=null
    init {
        getSearchedMovies("Star Wars")
    }
    fun getFilteredMovies(sortBy:String,genreIds: String?,minVote:Float,maxVote:Float,releaseDateGte:String,releaseDatelte:String,originalLanguage:String,voteCount:Int
    ){
        job?.cancel()
        job=getFilteredMoviesUseCase.getFilteredMovies(sortBy, genreIds, minVote, maxVote, releaseDateGte, releaseDatelte,originalLanguage,voteCount).onEach {
            when(it){
                is Resource.Error -> {
                    println(it.message)
                    _state.value = _state.value.copy(error = it.message ?: "Error")
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    println("Başarılı")
                    _state.value = _state.value.copy(movies = it.data, isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
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

            is SearchScreenEvent.ApplyFilter -> { getFilteredMovies(
                    "${event.selectedSortOption}.desc",event.selectedGenres.toString(),
                    event.voteAverageRange.start,event.voteAverageRange.endInclusive,
                    event.releaseYearRange.start.toString()+"-01-01",
                    event.releaseYearRange.endInclusive.toString()+"-12-31",
                    event.originalLanguage,event.voteCount)
            }
        }
    }
}