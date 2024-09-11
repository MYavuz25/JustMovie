package com.example.movieapp1.presentation.search_screen

sealed class SearchScreenEvent {
    data class Search(val searchString: String):SearchScreenEvent()
    data class ApplyFilter(
        val sortBy: String,
        val genreIds: String?,
        val minVote: Float,
        val maxVote: Float,
        val releaseDateGte: String,
        val releaseDateLte: String,
        val originalLanguage: String,
        val includeAdult:Boolean?=false,
        val voteCount: Int,
        val page: Int
    ):SearchScreenEvent()
}