package com.example.movieapp1.presentation.search_screen

sealed class SearchScreenEvent {
    data class Search(val searchString: String):SearchScreenEvent()
}