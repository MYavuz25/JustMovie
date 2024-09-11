package com.example.movieapp1.presentation.discover_screen

data class Question(val text: String,val answer1:String,val answer2: String)

data class FilterOptions(
    var sortBy: String? = null,
    var genreIds: String? = null,
    var minVote: Float? = null,
    var maxVote: Float? = null,
    var releaseDateGte: String? = null,
    var releaseDateLte: String? = null,
    var originalLanguage: String? = null,
    var includeAdult:Boolean?=false,
    var voteCount: Int? = null
)

