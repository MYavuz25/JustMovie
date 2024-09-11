package com.example.movieapp1.presentation.search_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp1.domain.use_case.get_filtered_movies.GetFilteredMoviesUseCase
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
) : ViewModel() {

    private val _state = mutableStateOf(SearchScreenState())
    val state: State<SearchScreenState> = _state
    private var job: Job? = null

    init {
        // Başlangıçta veri yüklemek için
        getSearchedMovies("Star Wars")
    }

    fun getFilteredMovies(
        sortBy: String,
        genreIds: String?,
        minVote: Float,
        maxVote: Float,
        releaseDateGte: String,
        releaseDateLte: String,
        originalLanguage: String,
        includeAdult:Boolean?=false,
        voteCount: Int,
        page: Int
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

    private fun getSearchedMovies(search: String) {
        job?.cancel()
        job = getSearchedMoviesUseCase.getSearchedMovies(search).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Error",
                        isLoading = false
                    )
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



    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.Search -> {
                getSearchedMovies(event.searchString)
            }

            is SearchScreenEvent.ApplyFilter -> {
                getFilteredMovies(
                    "${event.sortBy}.desc",
                    event.genreIds.toString(),
                    event.minVote,
                    event.maxVote,
                    "${event.releaseDateGte}-01-01",
                    "${event.releaseDateLte}-12-31",
                    event.originalLanguage,
                    event.includeAdult,
                    event.voteCount,
                    event.page // Sayfa numarasını burada kullanıyoruz
                )
            }
        }
    }
}
