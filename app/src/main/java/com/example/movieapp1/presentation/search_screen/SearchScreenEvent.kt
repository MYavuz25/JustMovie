package com.example.movieapp1.presentation.search_screen

sealed class SearchScreenEvent {
    data class Search(val searchString: String):SearchScreenEvent()
    data class ApplyFilter(
        val selectedGenres: String?,
        val releaseYearRange: ClosedFloatingPointRange<Float>,
        val voteAverageRange: ClosedFloatingPointRange<Float>,
        val selectedSortOption: String,
        val selectedLanguage: String,
        val originalLanguage:String,
        val voteCount:Int
    ):SearchScreenEvent()
}