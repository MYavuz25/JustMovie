package com.example.movieapp1.presentation.discover_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp1.domain.use_case.get_filtered_movies.GetFilteredMoviesUseCase
import com.example.movieapp1.presentation.search_screen.SearchScreenState
import com.example.movieapp1.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DiscoverScreenViewModel @Inject constructor(
    private val getFilteredMoviesUseCase: GetFilteredMoviesUseCase
):ViewModel() {

    private val _state = mutableStateOf(SearchScreenState())
    val state: State<SearchScreenState> = _state
    private var job: Job? = null

     fun getFilteredMovies(
        sortBy: String?,
        genreIds: String?,
        minVote: Float?,
        maxVote: Float?,
        releaseDateGte: String?,
        releaseDateLte: String?,
        originalLanguage: String?,
        includeAdult:Boolean?=false,
        voteCount: Int=100,
        page: Int=1
    ) {
        _state.value = _state.value.copy(isLoading = true)
        job?.cancel()
        job = getFilteredMoviesUseCase.getFilteredMovies(
            sortBy, genreIds, minVote, maxVote, releaseDateGte, releaseDateLte, originalLanguage,includeAdult, voteCount, page
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Error",
                        isLoading = false
                    )
                    println(result.message)
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        movies = result.data,
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}